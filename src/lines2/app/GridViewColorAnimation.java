package lines2.app;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class GridViewColorAnimation
		extends Animation
		implements AnimationListener {

	private GridView gridView;
	private int currentGridColor;
	private int animationColor;
	private boolean flag;

	public GridViewColorAnimation(GridView gridView, int animationColor) {
		this.gridView = gridView;
		this.animationColor = animationColor;
		this.flag = true;
		currentGridColor = gridView.getGridColor();
		setAnimationListener(this);
	}

	public void onAnimationEnd(Animation animation) {

	}

	public void onAnimationRepeat(Animation animation) {
		gridView.setGridColor(flag ? animationColor : currentGridColor);
		flag = !flag;
	}

	public void onAnimationStart(Animation animation) {
	}
}
