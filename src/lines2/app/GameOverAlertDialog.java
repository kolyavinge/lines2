package lines2.app;

import android.app.AlertDialog;
import android.content.Context;

public class GameOverAlertDialog {
	
	private Context context;

	public GameOverAlertDialog(Context context) {
		this.context = context;
	}

	public void show() {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Lines");
		alertDialog.setMessage("Игра окончена !");
		alertDialog.show();
	}
}
