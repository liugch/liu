package proxy.jdkproxy;


// 委托类, 要被代理的类
public class UserServiceImpl implements IUserService {

	@Override
	public void save() {
		System.out.println("调用dao层数据库操作");
	}

}
