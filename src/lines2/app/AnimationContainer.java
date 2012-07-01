package lines2.app;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class AnimationContainer {

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
