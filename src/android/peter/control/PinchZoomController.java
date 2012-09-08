package android.peter.control;

import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PinchZoomController implements OnTouchListener {

	short mode = -1; // not collecting data
						// 0 one finger down
	float distAtStartOfPinchSequence = -1f;
	float zoomAfterLastPinchSequence = 1.0f;
	float dist;
	float zoomMultiplier = 0.666f;

	public boolean onTouch(View v, MotionEvent event) {

		if (v instanceof ViewController) {
			ViewController vc = (ViewController) v;

			System.out.println("Pinch zoom touch");
			switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:
				mode = 0;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				if (mode == 0) {
					mode = 1;
					setZoom(vc, event);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mode = -1;
				zoomAfterLastPinchSequence = zoomMultiplier * dist
						/ distAtStartOfPinchSequence;
				distAtStartOfPinchSequence = -1f;
				break;
			case MotionEvent.ACTION_MOVE:
				setZoom(vc, event);
				break;
			}
		}

		return true;
	}

	public void setZoomFactor(int multiplier) {
		this.zoomMultiplier = (100 - multiplier) / 75f;
	}

	public void setZoom(ViewController vc, MotionEvent event) {
		if (mode == 1) {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);

			if (distAtStartOfPinchSequence == -1f) {
				distAtStartOfPinchSequence = FloatMath.sqrt(x * x + y * y);
			} else {
				dist = FloatMath.sqrt(x * x + y * y);

				if (dist > 10f)

					vc.setZoom(zoomMultiplier * zoomAfterLastPinchSequence
							* dist / distAtStartOfPinchSequence);
			}
		}
	}

}
