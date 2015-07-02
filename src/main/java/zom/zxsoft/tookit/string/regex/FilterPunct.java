package zom.zxsoft.tookit.string.regex;

import java.io.IOException;

public class FilterPunct {
	public static void main(String[] args) throws IOException {

		String string = "测试<>《》！*(^)$%~!@#$…&%￥—+=、。，；‘’“”：·`文本";
		System.out.println(string.replaceAll("\\pP|\\pS", " "));
		//		File file = new File("/home/fgq/Desktop/split.txt");
		//		BufferedReader reader = new BufferedReader(new FileReader(file));
		//		String temp = reader.readLine();
		//		while (temp != null) {
		//			String[] result = temp.replaceAll("\\pP|\\pS|\\pN|\\pZ|\\pC", " ").split("\\s{1,}");
		//			for (String res : result) {
		//				if (res.length() != 0) {
		//					System.out.println(res);
		//				}
		//			}
		//			temp = reader.readLine();
		//		}
	}
}
