package questionnaire.kmedoid;
import java.util.ArrayList;

import questionnaire.models.Answer;

public class KMedoidCluster {

	public ArrayList<Answer> ClusterMembers;
	public Answer Medoid;
	
	public KMedoidCluster(Answer medoid)
	{
		this.ClusterMembers = new ArrayList<Answer>();
		this.Medoid = medoid;
	}
	
	@Override
	public String toString() {
		String toPrintString = "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator");
		toPrintString += "Medoid: "+this.Medoid.toString() + System.getProperty("line.separator");
		/*
		for(Answer i : this.ClusterMembers)
		{
			toPrintString += i.toString() + System.getProperty("line.separator");
		}
		*/
		
		return toPrintString;
	}
	
}
