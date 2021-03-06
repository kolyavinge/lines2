package lines2.app;

import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

class AnimationContainer {

	public static Animation getIllegalMoveAnimation(GridView gridView) {
		Animation animation = new GridViewColorAnimation(gridView, Color.RED);
		animation.setDuration(100);
		animation.setRepeatCount(4);

		return animation;
	}
	
	public static Animation getFillAnimation() {
		Animation animation = new ScaleAnimation(
				0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(500);
		animation.setFillAfter(true);

		return animation;
	}

	public static Animation getEraseAnimation() {
		Animation animation = new ScaleAnimation(
				1f, 0f, 1f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(500);
		animation.setFillAfter(true);

		return animation;
	}
}
