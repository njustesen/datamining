package questionnaire.models;

public enum ProgrammingLanguage {
	
	CSHARP("C#"), 
	JAVA("Java"),
	PYTHON("Python"),
	JAVASCRIPT("JavaScript"),
	VB("VB"),
	CPLUSPLUS("C++"),
	C("C"),
	RUBY("RUBY"),
	FSHARP("F#"),
	OBJECTIVEC("Objective-C"),
	LISP("Lisp");
	
	String name;

	private ProgrammingLanguage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
