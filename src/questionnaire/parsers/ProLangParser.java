package questionnaire.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import questionnaire.models.ProgrammingLanguage;

public class ProLangParser {

	Map<String, ProgrammingLanguage> map;
	
	public List<ProgrammingLanguage> parse(List<String> strings){
		
		initMap();
		
		List<ProgrammingLanguage> langs = new ArrayList<ProgrammingLanguage>();
		
		for(String str : strings){
			
			if (map.containsKey(str.trim().toLowerCase()))
				langs.add(map.get(str.trim().toLowerCase()));
			
		}
		
		return langs;
		
	}

	private void initMap() {
		
		map = new HashMap<String, ProgrammingLanguage>();
		
		map.put("java", ProgrammingLanguage.JAVA);
		map.put("c#", ProgrammingLanguage.CSHARP);
		map.put("csharp", ProgrammingLanguage.CSHARP);
		map.put("c-sharp", ProgrammingLanguage.CSHARP);
		map.put("c sharp", ProgrammingLanguage.CSHARP);
		map.put("c #", ProgrammingLanguage.CSHARP);
		map.put("c++", ProgrammingLanguage.CPLUSPLUS);
		map.put("c ++", ProgrammingLanguage.CPLUSPLUS);
		map.put("c plus plus", ProgrammingLanguage.CPLUSPLUS);
		map.put("c plusplus", ProgrammingLanguage.CPLUSPLUS);
		map.put("f#", ProgrammingLanguage.FSHARP);
		map.put("fsharp", ProgrammingLanguage.FSHARP);
		map.put("f sharp", ProgrammingLanguage.FSHARP);
		map.put("f-sharp", ProgrammingLanguage.FSHARP);
		map.put("f #", ProgrammingLanguage.FSHARP);
		map.put("javascript", ProgrammingLanguage.JAVASCRIPT);
		map.put("java script", ProgrammingLanguage.JAVASCRIPT);
		map.put("java-script", ProgrammingLanguage.JAVASCRIPT);
		map.put("lisp", ProgrammingLanguage.LISP);
		map.put("objective-c", ProgrammingLanguage.OBJECTIVEC);
		map.put("objective c", ProgrammingLanguage.OBJECTIVEC);
		map.put("obj-c", ProgrammingLanguage.OBJECTIVEC);
		map.put("obj c", ProgrammingLanguage.OBJECTIVEC);
		map.put("objc", ProgrammingLanguage.OBJECTIVEC);
		map.put("python", ProgrammingLanguage.PYTHON);
		map.put("ruby", ProgrammingLanguage.RUBY);
		map.put("vb", ProgrammingLanguage.VB);
		map.put("visual basic", ProgrammingLanguage.VB);
		map.put("visualbasic", ProgrammingLanguage.VB);
		map.put("vb.net", ProgrammingLanguage.VB);
		map.put("vb net", ProgrammingLanguage.VB);
		map.put("vbnet", ProgrammingLanguage.VB);
	}
	
}
