package org.nift4.qseh.fake;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.nift4.qseh.QsHeaderImpl;
import org.nift4.qseh.lib.QsInterface;
import org.nift4.qseh.lib.QsTile;

public class TilePreview extends LinearLayout implements QsInterface {
	private final QsHeaderImpl mImpl;

	public TilePreview(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		mImpl = new QsHeaderImpl(context, this, this);
		addView(mImpl.getView());
	}

	@Override
	public QsTile createTile(String var1) {
		return var1.equals("flashlight") ? new FlashlightTile() : null;
	}
}
