package org.ansj;

import org.ansj.domain.Result;
import org.ansj.library.AmbiguityLibrary;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

public class Test {
	public static void main(String[] args) throws Exception {

		// 构造一个用户词典
		Forest forest = Library.makeForest("library/default2.dic");
		forest = new Forest();

		// 增加新词,中间按照'\t'隔开
		DicLibrary.insert(DicLibrary.DEFAULT, "ansj中文分词", "userDefine", 1000);
		Result terms = ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!爱国者黑客按内容存取存储器阿里通");
		System.out.println("增加新词例子:" + terms);

		// 删除词语,只能删除.用户自定义的词典.
		DicLibrary.delete(DicLibrary.DEFAULT, "ansj中文分词");
		terms = ToAnalysis.parse("我觉得ansj中文分词是一个不错的系统!我是王婆!");
		System.out.println("删除用户自定义词典例子:" + terms);

		// 歧义词
		Value value = new Value("济南下车", "济南", "n", "下车", "v");
		System.out.println(ToAnalysis.parse("我经济南下车到广州.中国经济南下势头迅猛!"));
		AmbiguityLibrary.insert(AmbiguityLibrary.DEFAULT, value);
		System.out.println(ToAnalysis.parse("我经济南下车到广州.中国经济南下势头迅猛!"));

		// 多用户词典
		String str = "神探夏洛克这部电影作者.是一个dota迷";
		System.out.println(ToAnalysis.parse(str));
		// 两个词汇 神探夏洛克 douta迷
		Forest dic1 = new Forest();
		Library.insertWord(dic1, new Value("神探夏洛克", "define", "1000"));
		Forest dic2 = new Forest();
		Library.insertWord(dic2, new Value("dota迷", "define", "1000"));
		System.out.println(ToAnalysis.parse(str, dic1, dic2));
	}
}
