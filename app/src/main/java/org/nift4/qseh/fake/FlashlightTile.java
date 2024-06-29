package org.nift4.qseh.fake;

public class FlashlightTile extends FakeTileImpl {
	@Override
	public String getTileSpec() {
		return "flashlight";
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public CharSequence getTileLabel() {
		return "FlashlightFake";
	}

	@Override
	public State getState() {
		BooleanState s = new BooleanState();
		super.fillState(s);
		s.secondaryLabel = "On";
		s.value = true;
		return s;
	}
}
