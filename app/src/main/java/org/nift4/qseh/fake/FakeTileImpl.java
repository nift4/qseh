package org.nift4.qseh.fake;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

import org.nift4.qseh.R;
import org.nift4.qseh.lib.QsTile;

public abstract class FakeTileImpl implements QsTile {


	@Override
	public void setTileSpec(String var1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void refreshState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addCallback(Callback var1) {
		// no-op
	}

	@Override
	public void removeCallback(Callback var1) {
		// no-op
	}

	@Override
	public void removeCallbacks() {
		// no-op
	}

	@Override
	public void click(@Nullable View var1) {
		// no-op
	}

	@Override
	public void secondaryClick(@Nullable View var1) {
		// no-op
	}

	@Override
	public void longClick(@Nullable View var1) {
		// no-op
	}

	@Override
	public void userSwitch(int var1) {
		// no-op
	}

	@Override
	public void setListening(Object var1, boolean var2) {
		// no-op
	}

	@Override
	public void setDetailListening(boolean var1) {
		// no-op
	}

	@Override
	public void destroy() {
		// no-op
	}

	@Override
	public boolean isListening() {
		return true;
	}

	@Override
	public boolean isTileReady() {
		return true;
	}

	protected void fillState(State s) {
		s.spec = getTileSpec();
		s.label = getTileLabel();
		s.secondaryLabel = "subtitle";
		s.iconSupplier = () -> new Icon() {
			@Override
			public Drawable getDrawable(Context var1) {
				return var1.getDrawable(R.drawable.baseline_texture_24);
			}
		};
	}
}
