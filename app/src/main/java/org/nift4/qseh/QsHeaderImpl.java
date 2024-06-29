package org.nift4.qseh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.nift4.qseh.lib.QsInterface;
import org.nift4.qseh.lib.QsTile;

public class QsHeaderImpl {
	private final QsInterface mIface;
	private final View mView;

	public QsHeaderImpl(Context context, ViewGroup root, QsInterface iface) {
		mIface = iface;
		mView = LayoutInflater.from(context).inflate(R.layout.sample_content, root, false);
		QsTile flashlight = mIface.createTile("flashlight");
		Button b1 = mView.findViewById(R.id.button1);
		b1.setEnabled(false);
		b1.setText(flashlight.getTileLabel());
		Button b2 = mView.findViewById(R.id.button2);
		b2.setText(flashlight.getTileLabel());
		b2.setOnClickListener(v -> {
			// click
			flashlight.click(v);
		});
	}

	public View getView() {
		return mView;
	}
}
