package org.nift4.qseh.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.systemui.plugins.annotations.Requires;
import com.android.systemui.plugins.qs.QSExtraHeader;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;

import org.nift4.qseh.QsHeaderImpl;

import java.util.HashMap;

@Requires(target = QSExtraHeader.class, version = QSExtraHeader.VERSION)
@Requires(target = QSIconView.class, version = QSIconView.VERSION)
@Requires(target = QSTile.class, version = QSTile.VERSION)
@Requires(target = QSTile.Callback.class, version = QSTile.Callback.VERSION)
@Requires(target = QSTile.Icon.class, version = QSTile.Icon.VERSION)
@Requires(target = QSTile.State.class, version = QSTile.State.VERSION)
public class QsHeaderPlugin implements QSExtraHeader {
	@SuppressLint("StaticFieldLeak")
	private static QsHeaderPlugin INSTANCE;
	private static final String TAG = "QsHeaderPlugin";
	private Context mPluginContext;
	//private Context mSysuiContext;
	private QsHeaderImpl mImpl;

	@Override
	public void onCreate(Context sysuiContext, Context pluginContext) {
		Log.d(TAG, "onCreate");
		INSTANCE = this;
		//mSysuiContext = sysuiContext;
		mPluginContext = pluginContext;
	}

	@Override
	public void onDestroy() {
		INSTANCE = null;
	}

	@Override
	public View createHeaderView(Interface iface) {
		if (mImpl != null) {
			throw new IllegalStateException("createHeaderView called twice?");
		}
		mImpl = new QsHeaderImpl(mPluginContext, new LinearLayout(mPluginContext), new IfaceAdapter(iface));
		return mImpl.getView();
	}

	private static class IfaceAdapter implements QsInterface {
		private final Interface mIface;

		public IfaceAdapter(Interface iface) {
			mIface = iface;
		}

		@Override
		public QsTile createTile(String tileSpec) {
			return new QsTileAdapter(mIface.createTile(tileSpec));
		}
	}

	private static class QsTileAdapter implements QsTile {
		private final QSTile mTile;
		private final HashMap<Callback, QSTile.Callback> mCallbackAdapters = new HashMap<>();

		public QsTileAdapter(QSTile tile) {
			mTile = tile;
		}

		@Override
		public String getTileSpec() {
			return mTile.getTileSpec();
		}

		@Override
		public boolean isAvailable() {
			return mTile.isAvailable();
		}

		@Override
		public void setTileSpec(String var1) {
			mTile.setTileSpec(var1);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void clearState() {
			mTile.clearState();
		}

		@Override
		public void refreshState() {
			mTile.refreshState();
		}

		@Override
		public void addCallback(Callback var1) {
			QSTile.Callback cb = new CallbackAdapter(var1);
			mCallbackAdapters.put(var1, cb);
			mTile.addCallback(cb);
		}

		@Override
		public void removeCallback(Callback var1) {
			mTile.removeCallback(mCallbackAdapters.remove(var1));
		}

		@Override
		public void removeCallbacks() {
			mTile.removeCallbacks();
			mCallbackAdapters.clear();
		}

		@Override
		public void click(@Nullable View var1) {
			mTile.click(var1);
		}

		@Override
		public void secondaryClick(@Nullable View var1) {
			mTile.secondaryClick(var1);
		}

		@Override
		public void longClick(@Nullable View var1) {
			mTile.longClick(var1);
		}

		@Override
		public void userSwitch(int var1) {
			mTile.userSwitch(var1);
		}

		@Override
		public void setListening(Object var1, boolean var2) {
			mTile.setListening(var1, var2);
		}

		@Override
		public void setDetailListening(boolean var1) {
			mTile.setDetailListening(var1);
		}

		@Override
		public void destroy() {
			mTile.destroy();
		}

		@Override
		public CharSequence getTileLabel() {
			return mTile.getTileLabel();
		}

		@Override
		public State getState() {
			return StateAdapter.of(mTile.getState());
		}

		@Override
		public boolean isTileReady() {
			return mTile.isTileReady();
		}

		@Override
		public boolean isListening() {
			return mTile.isListening();
		}
	}

	private static class CallbackAdapter implements QSTile.Callback {
		private final QsTile.Callback mCb;

		public CallbackAdapter(QsTile.Callback cb) {
			mCb = cb;
		}

		@Override
		public void onStateChanged(QSTile.State state) {
			mCb.onStateChanged(StateAdapter.of(state));
		}
	}

	private static final class IconAdapter extends QsTile.Icon {
		private final QSTile.Icon mIcon;

		public IconAdapter(QSTile.Icon icon) {
			mIcon = icon;
		}

		@Override
		public Drawable getDrawable(Context var1) {
			return mIcon.getDrawable(INSTANCE.mPluginContext);
		}

		@Override
		public Drawable getInvisibleDrawable(Context var1) {
			return mIcon.getInvisibleDrawable(INSTANCE.mPluginContext);
		}

		@Override
		public int getPadding() {
			return mIcon.getPadding();
		}

		@NonNull
		@Override
		public String toString() {
			return mIcon.toString();
		}

		@Override
		public int hashCode() {
			return mIcon.hashCode();
		}
	}

	private static final class SlashStateAdapter extends QsTile.SlashState {
		public SlashStateAdapter(QSTile.SlashState state) {
			isSlashed = state.isSlashed;
			rotation = state.rotation;
		}
	}

	private static final class StateAdapter extends QsTile.State {
		private final QSTile.State mState;

		public StateAdapter(QSTile.State inState) {
			mState = inState;
			// == base ==
			icon = new IconAdapter(mState.icon);
			iconSupplier = mState.iconSupplier != null ?
					() -> new IconAdapter(mState.iconSupplier.get()) : null;
			state = mState.state;
			label = mState.label;
			secondaryLabel = mState.secondaryLabel;
			contentDescription = mState.contentDescription;
			stateDescription = mState.stateDescription;
			dualLabelContentDescription = mState.dualLabelContentDescription;
			disabledByPolicy = mState.disabledByPolicy;
			dualTarget = mState.dualTarget;
			isTransient = mState.isTransient;
			expandedAccessibilityClassName = mState.expandedAccessibilityClassName;
			slash = new SlashStateAdapter(mState.slash);
			handlesLongClick = mState.handlesLongClick;
			showRippleEffect = mState.showRippleEffect;
			sideViewCustomDrawable = mState.sideViewCustomDrawable;
			spec = mState.spec;
		}

		@Override
		public String getStateText(int var1, Resources var2) {
			return mState.getStateText(var1, var2);
		}

		@Override
		public String getSecondaryLabel(String var1) {
			return mState.getSecondaryLabel(var1);
		}

		public static QsTile.State of(QSTile.State state) {
			if (state instanceof QSTile.SignalState) {
				return new SignalStateAdapter((QSTile.SignalState) state);
			} else if (state instanceof QSTile.BooleanState) {
				return new BooleanStateAdapter((QSTile.BooleanState) state);
			} else {
				return new StateAdapter(state);
			}
		}
	}

	private static class BooleanStateAdapter extends QsTile.BooleanState {
		private final QSTile.BooleanState mState;

		public BooleanStateAdapter(QSTile.BooleanState inState) {
			mState = inState;
			// == base ==
			icon = new IconAdapter(mState.icon);
			iconSupplier = mState.iconSupplier != null ?
					() -> new IconAdapter(mState.iconSupplier.get()) : null;
			state = mState.state;
			label = mState.label;
			secondaryLabel = mState.secondaryLabel;
			contentDescription = mState.contentDescription;
			stateDescription = mState.stateDescription;
			dualLabelContentDescription = mState.dualLabelContentDescription;
			disabledByPolicy = mState.disabledByPolicy;
			dualTarget = mState.dualTarget;
			isTransient = mState.isTransient;
			expandedAccessibilityClassName = mState.expandedAccessibilityClassName;
			slash = new SlashStateAdapter(mState.slash);
			handlesLongClick = mState.handlesLongClick;
			showRippleEffect = mState.showRippleEffect;
			sideViewCustomDrawable = mState.sideViewCustomDrawable;
			spec = mState.spec;
			// == bool ==
			value = mState.value;
			forceExpandIcon = mState.forceExpandIcon;
		}

		@Override
		public String getStateText(int var1, Resources var2) {
			return mState.getStateText(var1, var2);
		}

		@Override
		public String getSecondaryLabel(String var1) {
			return mState.getSecondaryLabel(var1);
		}
	}

	private static class SignalStateAdapter extends QsTile.SignalState {
		private final QSTile.SignalState mState;

		public SignalStateAdapter(QSTile.SignalState inState) {
			mState = inState;
			// == base ==
			icon = new IconAdapter(mState.icon);
			iconSupplier = mState.iconSupplier != null ?
					() -> new IconAdapter(mState.iconSupplier.get()) : null;
			state = mState.state;
			label = mState.label;
			secondaryLabel = mState.secondaryLabel;
			contentDescription = mState.contentDescription;
			stateDescription = mState.stateDescription;
			dualLabelContentDescription = mState.dualLabelContentDescription;
			disabledByPolicy = mState.disabledByPolicy;
			dualTarget = mState.dualTarget;
			isTransient = mState.isTransient;
			expandedAccessibilityClassName = mState.expandedAccessibilityClassName;
			slash = new SlashStateAdapter(mState.slash);
			handlesLongClick = mState.handlesLongClick;
			showRippleEffect = mState.showRippleEffect;
			sideViewCustomDrawable = mState.sideViewCustomDrawable;
			spec = mState.spec;
			// == bool ==
			value = mState.value;
			forceExpandIcon = mState.forceExpandIcon;
			// == signal ==
			activityIn = mState.activityIn;
			activityOut = mState.activityOut;
			isOverlayIconWide = mState.isOverlayIconWide;
			overlayIconId = mState.overlayIconId;
		}

		@Override
		public String getStateText(int var1, Resources var2) {
			return mState.getStateText(var1, var2);
		}

		@Override
		public String getSecondaryLabel(String var1) {
			return mState.getSecondaryLabel(var1);
		}
	}
}
