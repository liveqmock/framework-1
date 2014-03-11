package framework;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mopon.dao.master.member.IGroupDao;
import com.mopon.entity.member.Group;
import com.mopon.util.Base64Utils;
import com.mopon.util.KeyGenerater;
import com.mopon.util.MailUtil;
import com.mopon.util.SignProvider;
import com.mopon.util.Signaturer;
import com.mopon.entity.member.Group;
import com.mopon.entity.sys.MailTask;
import com.mopon.entity.sys.MessageEntity;
import com.mopon.jms.TopicMessageProducer;
import com.mopon.jms.impl.MessageMdp;

public class TestMain {

	private static ApplicationContext ctx;

/*java.version
	

Java 运行时环境版本

java.vendor
	

Java 运行时环境供应商

java.vendor.url
	

Java 供应商的 URL

java.home
	

Java 安装目录

java.vm.specification.version
	

Java 虚拟机规范版本

java.vm.specification.vendor
	

Java 虚拟机规范供应商

java.vm.specification.name
	

Java 虚拟机规范名称

java.vm.version
	

Java 虚拟机实现版本

java.vm.vendor
	

Java 虚拟机实现供应商

java.vm.name
	

Java 虚拟机实现名称

java.specification.version
	

Java 运行时环境规范版本

java.specification.vendor
	

Java 运行时环境规范供应商

java.specification.name
	

Java 运行时环境规范名称

java.class.version
	

Java 类格式版本号

java.class.path
	

Java 类路径

java.library.path
	

加载库时搜索的路径列表

java.io.tmpdir
	

默认的临时文件路径

java.compiler
	

要使用的 JIT 编译器的名称

java.ext.dirs
	

一个或多个扩展目录的路径

os.name
	

操作系统的名称

os.arch
	

操作系统的架构

os.version
	

操作系统的版本

file.separator
	

文件分隔符（在 UNIX 系统中是“/”）

path.separator
	

路径分隔符（在 UNIX 系统中是“:”）

line.separator
	

行分隔符（在 UNIX 系统中是“/n”）

user.name
	

用户的账户名称

user.home
	

用户的主目录

user.dir
	

用户的当前工作目录*/
	

	public static void main(String[] args) throws Exception {
		
		/*KeyGenerater kg = new KeyGenerater();
		kg.generater();
		byte[] priKey = kg.getPriKey();
		byte[] pubKey = kg.getPubKey();
		
		Signaturer si = new Signaturer();
		byte[] sign = si.sign(priKey, "a");
		System.out.println(new String(sign));
		SignProvider sp = new SignProvider();
		System.out.println(sp.verify(pubKey, "a", sign));
		System.out.println(java.util.UUID.randomUUID().toString().hashCode());*/
		/*FileInputStream in = new FileInputStream("c:/order_02_20131015.txt");
		DataInputStream  din = new DataInputStream(in);
		String line = "";
		line = din.readLine();
		System.out.println(line.trim().split("\\^")[0]);
		din.close();
		in.close();*/
		/*System.out.println(new Date());
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/config/spring/mail.xml");  
		MailUtil sendMail = (MailUtil) ctx.getBean("mail");
		MailTask mt = new MailTask();
		mt.setMailAddress("reaganjava@163.com");
		mt.setSubject("Hello world");
		mt.setFrom("ligeng.sz@mopon.cn");
		mt.setContent("Hello wolrd");
		File file = new File("d:/项目部署说明书.doc");
		mt.getAttachments().add(file);
		sendMail.addMailTaskQueue(mt);*/
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/config/spring/jms.xml");  
		TopicMessageProducer tp = (TopicMessageProducer) ctx.getBean("topicMessageProducer");
		System.out.println(tp);
		for(int i = 0; i < 10; i++) {
			MessageEntity me = new MessageEntity();
			me.setTextBody("Hello");
			System.out.println(i);
			tp.send(me);
		}
		
		MessageMdp mm = (MessageMdp) ctx.getBean("topicMessageMdp");
	}

}
