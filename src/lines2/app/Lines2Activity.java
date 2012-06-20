package lines2.app;

import lines2.model.Field;
import lines2.model.FieldLoader;
import android.app.Activity;
import android.os.Bundle;

public class Lines2Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FieldLoader fieldLoader = new FieldLoader();
		Field field = fieldLoader.getField(10, 8);
		
		FieldPresenter fieldPresenter = new FieldPresenter();
		
		FieldView fieldView = new FieldView(this);
		fieldView.setPresenter(fieldPresenter);
		
		fieldPresenter.setField(field);
		fieldPresenter.setView(fieldView);
		
		setContentView(fieldView);
	}
}
