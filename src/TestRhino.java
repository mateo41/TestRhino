
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
		
		public void set_testBool(boolean b){
			testBool = b;
		}
		
	}
		
	public static void main(String [] args) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException{
			Context context = Context.enter();
			SimpleObject simple = new TestRhino.SimpleObject("testID", true);
			try {
				Scriptable scope = context.initStandardObjects();
				
				scope.put("simple", scope, simple);
				String js = "var v = 0; if (simple.testBool) { v = 1} else { v = 2}";
				
				Script script = context.compileReader(new StringReader(js), "Test", 1, null);
				script.exec(context, scope);
				
				System.out.println(scope.get("v", scope));
				System.out.println(scope.get("simple", scope));
				System.out.println(scope.get("simple.testString", scope));
				System.out.println(scope.get("simple.testBool", scope));

			} 
			finally {
				Context.exit();
			}
		}
}
