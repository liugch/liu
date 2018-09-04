package com.edu.snmp.groovy.util;

import com.edu.model.User;
import com.google.common.base.Preconditions;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableListener;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public final class SnmpGetUtil {
    public static SnmpValue getOneValue(final Map<String, String> config, final String oid) throws IOException {

        SnmpExecutor se = new SnmpExecutor();
        se.setAgentAddress(config.get("host") + "/" + config.get("port"));
        se.setReadCommunity(config.get("readCommunity"));
        se.setVersion(SnmpExecutor.V1);


        //保持原子性
        //赋值操作不是线程安全的。若想不用锁来实现，可以用AtomicReference<V>这个类，实现对象引用的原子更新。
        //使用场景：一个线程使用student对象，另一个线程负责定时读表，更新这个对象。那么就可以用AtomicReference这个类。
        final AtomicReference<SnmpValue> snmpValue = new AtomicReference<SnmpValue>();

        se.execute(new SnmpHandler() {
            @Override
            public void handle(Snmp snmp, Target target) throws IOException {
                PDU request = new PDU();
                request.setType(PDU.GET);
                request.add(new VariableBinding(new OID(oid)));
                // 线程安全的阻塞队列 可以用TransferQuenue 代替 BlockingQueue 做了更好的优化
                final TransferQueue<ResponseEvent> bq = new LinkedTransferQueue<ResponseEvent>();
               // final BlockingQueue<ResponseEvent> bq = new ArrayBlockingQueue<ResponseEvent>(1);
                snmp.send(request, target, null,
                        new ResponseListener() {
                            @Override
                            public void onResponse(ResponseEvent event) {
                                try {
                                    bq.put(event);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                ResponseEvent responseEvent = null;
                try {
                    responseEvent = bq.poll(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (responseEvent != null) {
                    PDU response = responseEvent.getResponse();

                    //用于判断 response 的值是否为空, 并提示报错的信息, 在方法执行之前就判断参数是否为空,
                    //不用等到方法执行到很深的时候报错,更不会浪费情况
                    Preconditions.checkNotNull(response, target.getAddress() + " no response");

                    if (response != null) {
                        //当返回值状态没错时执行
                        if ((response.getErrorIndex() == PDU.noError) && (response.getErrorStatus() == PDU.noError)) {
                            if (responseEvent.getResponse().getVariableBindings().size() == 0) {
                                return;
                            }
                            VariableBinding vb = response.getVariableBindings().get(0);//获取第一条
                            Variable va = vb.getVariable();
                            snmpValue.set(new SnmpValue(vb.getOid().toString(), va.toString(), va.getSyntaxString(), va));
                        }
                    }
                }
            }
        });

        return snmpValue.get();
    }

    public static String[] getOneTableValue(final Map<String, String> config, final String[] oidList) throws IOException {

        String[][] oids = getTableValue(config, oidList);

        String[] values = new String[oids[0].length];

        for (String[] aaa : oids) {
            for (int j = 0; j < aaa.length; j++) {
                if ((aaa[j] != null) && (aaa[j].trim().length() > 0)) {
                    values[j] = aaa[j];
                }
            }
        }

        return values;
    }

    /**
     * 默认第一列为 index
     *
     * @param snmp
     * @param target
     * @param pduFactory
     * @param oidList
     * @return
     */
    public static String[][] getTableValue(final Map<String, String> config, final String[] oidList) throws IOException {

        SnmpExecutor se = new SnmpExecutor();
        se.setAgentAddress(config.get("host") + "/" + config.get("port"));
        se.setReadCommunity(config.get("readCommunity"));
        se.setVersion(SnmpExecutor.V1);
        // 保持数据的原子性
        final AtomicReference<String[][]> snmpValue = new AtomicReference<String[][]>();

        se.execute(new SnmpHandler() {
            @Override
            public void handle(Snmp snmp, Target target) throws IOException {

                DefaultPDUFactory pduFactory = new DefaultPDUFactory();

                OID[] columnOIDs = new OID[oidList.length];

                for (int i = 0; i < oidList.length; i++) {
                    columnOIDs[i] = new OID(oidList[i]);
                }

                TableUtils table = new TableUtils(snmp, pduFactory);
                final BlockingQueue<List<TableEvent>> bq = new ArrayBlockingQueue<List<TableEvent>>(1);
                table.getTable(target, columnOIDs,
                        new TableListener() {
                            List<TableEvent> tableEventList = new ArrayList<TableEvent>();
                            boolean finished = false;

                            @Override
                            public boolean next(TableEvent event) {
                                tableEventList.add(event);
                                return true;
                            }

                            @Override
                            public void finished(TableEvent event) {
                                if ((event.getStatus() != TableEvent.STATUS_OK) ||
                                        (event.getIndex() != null)) {
                                    tableEventList.add(event);
                                }
                                finished = true;
                                try {
                                    bq.put(tableEventList);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public boolean isFinished() {
                                return finished;
                            }

                        },
                        null, null, null);
                List<TableEvent> tableEventList = null;
                try {
                    tableEventList = bq.poll(20, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tableEventList != null) {
                    // 有时采集不到值，先用 list 保存，然后转换成 String[][]
                    ArrayList<String[]> valueList = new ArrayList<String[]>();

                    for (int index = 0; index < tableEventList.size(); index++) {
                        TableEvent event = tableEventList.get(index);

                        Preconditions.checkState(TableEvent.STATUS_OK == event.getStatus(), target.getAddress() + " "
                                + event.getErrorMessage());

                        // 有时会无法采集到值
                        if ((event.getIndex() == null) || (event.getColumns() == null)) {
                            continue;
                        }

                        String[] oneLineValues = new String[oidList.length + 1];

                        oneLineValues[0] = event.getIndex().toString();

                        for (int i = 0; i < oidList.length; i++) {
                            VariableBinding variableBinding = event.getColumns()[i];

                            if (variableBinding == null) {
                                continue;
                            }

                            oneLineValues[i + 1] = variableBinding.getVariable().toString();
                        }

                        valueList.add(oneLineValues);
                    }

                    String[][] values = new String[valueList.size()][oidList.length + 1];

                    for (int i = 0; i < valueList.size(); i++) {
                        values[i] = valueList.get(i);
                    }


                    snmpValue.set(values);
                } else {
                    throw new IOException("snmp time out!");
                }
            }
        });

        return snmpValue.get();
    }

}
