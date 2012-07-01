package lines2.app;

import java.util.ArrayList;
import java.util.Collection;

import lines2.model.Ball;
import lines2.model.Cell;
import lines2.model.DefaultFieldListener;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class FieldView extends ViewGroup {

	private static final int topOffset = 2;
	private static final int ballOffset = 4;

	private FieldPresenter presenter;
	private BallViewFactory ballViewFactory;
	private GridView gridView;

	public FieldView(FieldPresenter presenter, BallViewFactory ballViewFactory, Context context) {
		super(context);
		setPresenter(presenter);
		setBallViewFactory(ballViewFactory);
		createGridView(presenter, context);
	}

	private void createGridView(FieldPresenter presenter, Context context) {
		gridView = new GridView(context);
		gridView.setColor(Color.WHITE);
		gridView.setSize(presenter.getField().getRows(), presenter.getField().getCols());
		addView(gridView);
	}

	private void setBallViewFactory(BallViewFactory ballViewFactory) {
		this.ballViewFactory = ballViewFactory;
	}

	private void setPresenter(FieldPresenter presenter) {
		this.presenter = presenter;
		this.presenter.getField().addFieldListener(fieldListener);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		removeAllBallViews();
		addBallViews();
		addNextFillBallViews();
		layoutGridView(left, top, right, bottom);
		layoutBallViews();
	}

	private void addBallViews() {
		for (Cell cell : presenter.getFieldCells()) {
			if (cell.isEmpty() == false) {
				BallView ballView = ballViewFactory.getBallView(cell.getBall());
				addView(ballView);
			}
		}
	}

	private void addNextFillBallViews() {
		for (Ball ball : presenter.getField().getNextFillCells()) {
			BallView ballView = ballViewFactory.getNextBallView(ball);
			addView(ballView);
		}
	}

	private void layoutBallViews() {
		for (BallView ballView : getBallViews())
			layoutBallView(ballView);
	}

	private void layoutBallView(BallView ballView) {
		Ball ball = ballView.getBall();
		float row = ball.getCell().getRow();
		float col = ball.getCell().getCol();

		float left = getCellSize() * col + ballOffset;
		float top = getCellSize() * row + ballOffset + topOffset;
		float right = left + getCellSize() - 2f * ballOffset;
		float bottom = top + getCellSize() - 2f * ballOffset;

		ballView.layout((int) left, (int) top, (int) right, (int) bottom);
	}

	private void layoutGridView(int left, int top, int right, int bottom) {
		gridView.layout(left, top + topOffset, right, bottom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int row = (int) (event.getY() / getCellSize());
		int col = (int) (event.getX() / getCellSize());
		presenter.selectCell(row, col);
		setOrClearGridSelectedCell(row, col);

		return super.onTouchEvent(event);
	}

	private void setOrClearGridSelectedCell(int row, int col) {
		if (presenter.noSelectedCell())
			gridView.clearSelectedCell();
		else
			gridView.setSelectedCell(row, col);

		gridView.postInvalidate();
	}

	private float getCellSize() {
		return gridView.getCellSize();
	}

	private BallView findBallView(Ball ball) {
		for (BallView ballView : getBallViews()) {
			if (ballView.getBall().equals(ball))
				return ballView;
		}

		throw new IllegalArgumentException();
	}

	private void removeAllBallViews() {
		for (BallView ballView : getBallViews())
			removeView(ballView);
	}

	private Collection<BallView> getBallViews() {
		Collection<BallView> result = new ArrayList<BallView>();

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			if (child instanceof BallView)
				result.add((BallView) child);
		}

		return result;
	}

	/* -------------------- DefaultFieldListener -------------------- */

	private final DefaultFieldListener fieldListener = new DefaultFieldListener() {

		@Override
		public void onBallMove(Cell from, Cell to) {
			Ball ball = to.getBall();
			BallView ballView = findBallView(ball);
			layoutBallView(ballView);
			requestLayout();
		}

		@Override
		public void onFillCells(Collection<Cell> filledCells) {
			requestLayout();
		}

		@Override
		public void onEraseCells(Collection<Cell> erasedCells) {
			for (Cell cell : erasedCells) {
				Ball ball = cell.getBall();
				BallView ballView = findBallView(ball);
				ballView.startAnimation(AnimationContainer.getEraseAnimation());
			}
		}
	};
}
