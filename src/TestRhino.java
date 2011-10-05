
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;



public class TestRhino {
	public static class SimpleObject{
		private String testString;
		private boolean testBool;
		
		public SimpleObject(String testStr, boolean testB){
			testString = testStr;
			testBool = testB;
		}
		
		public String gettestString(){
			return testString;
		}
		
		public boolean gettestBool(){
			return testBool;
		}
		
		public void log(String msg){
			System.out.println(msg);
		}
		
	}
		
	public static void main(String [] args) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException{
			Context context = Context.enter();
		
			try {
				Scriptable scope = context.initStandardObjects();
				SimpleObject simple = new SimpleObject("test", true);
				scope.put("s", scope, simple);
				
				String js2 = "s.log(s.testBool); s.log(s.testString);";
				Script script2 = context.compileReader(new StringReader(js2), "Test2", 1, null);
				script2.exec(context, scope);
				

			} 
			finally {
				Context.exit();
			}
		}
}
