package org.nift4.qseh.lib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

// v4
public interface QsTile {
	String getTileSpec();

	boolean isAvailable();

	void setTileSpec(String var1);

	/** @deprecated */
	@Deprecated
	default void clearState() {
	}

	void refreshState();

	void addCallback(Callback var1);

	void removeCallback(Callback var1);

	void removeCallbacks();

	void click(@Nullable View var1);

	void secondaryClick(@Nullable View var1);

	void longClick(@Nullable View var1);

	void userSwitch(int var1);

	void setListening(Object var1, boolean var2);

	void setDetailListening(boolean var1);

	void destroy();

	CharSequence getTileLabel();

	State getState();

	default boolean isTileReady() {
		return false;
	}

	boolean isListening();

	public static class SlashState {
		public boolean isSlashed;
		public float rotation;

		public SlashState() {
		}

		@NonNull
		public String toString() {
			return "isSlashed=" + this.isSlashed + ",rotation=" + this.rotation;
		}

		public boolean equals(Object var1) {
			if (var1 == null) {
				return false;
			} else {
				try {
					return ((SlashState)var1).rotation == this.rotation && ((SlashState)var1).isSlashed == this.isSlashed;
				} catch (ClassCastException var3) {
					return false;
				}
			}
		}

		public SlashState copy() {
			SlashState var1 = new SlashState();
			var1.rotation = this.rotation;
			var1.isSlashed = this.isSlashed;
			return var1;
		}
	}

	public static /*final*/ class SignalState extends BooleanState {
		public static final int VERSION = 1;
		public boolean activityIn;
		public boolean activityOut;
		public boolean isOverlayIconWide;
		public int overlayIconId;

		public SignalState() {
		}

		public boolean copyTo(State var1) {
			SignalState var2 = (SignalState)var1;
			boolean var3 = var2.activityIn != this.activityIn || var2.activityOut != this.activityOut || var2.isOverlayIconWide != this.isOverlayIconWide || var2.overlayIconId != this.overlayIconId;
			var2.activityIn = this.activityIn;
			var2.activityOut = this.activityOut;
			var2.isOverlayIconWide = this.isOverlayIconWide;
			var2.overlayIconId = this.overlayIconId;
			return super.copyTo(var1) || var3;
		}

		protected StringBuilder toStringBuilder() {
			StringBuilder var1 = super.toStringBuilder();
			var1.insert(var1.length() - 1, ",activityIn=" + this.activityIn);
			var1.insert(var1.length() - 1, ",activityOut=" + this.activityOut);
			return var1;
		}

		public State copy() {
			SignalState var1 = new SignalState();
			this.copyTo(var1);
			return var1;
		}
	}

	public static class BooleanState extends State {
		public static final int VERSION = 1;
		public boolean value;
		public boolean forceExpandIcon;

		public BooleanState() {
		}

		public boolean copyTo(State var1) {
			BooleanState var2 = (BooleanState)var1;
			boolean var3 = super.copyTo(var1) || var2.value != this.value || var2.forceExpandIcon != this.forceExpandIcon;
			var2.value = this.value;
			var2.forceExpandIcon = this.forceExpandIcon;
			return var3;
		}

		protected StringBuilder toStringBuilder() {
			StringBuilder var1 = super.toStringBuilder();
			var1.insert(var1.length() - 1, ",value=" + this.value);
			var1.insert(var1.length() - 1, ",forceExpandIcon=" + this.forceExpandIcon);
			return var1;
		}

		public State copy() {
			BooleanState var1 = new BooleanState();
			this.copyTo(var1);
			return var1;
		}
	}

	public static class State {
		public static final int DEFAULT_STATE = 1;
		public Icon icon;
		public Supplier<Icon> iconSupplier;
		public int state = 1;
		public CharSequence label;
		@Nullable
		public CharSequence secondaryLabel;
		public CharSequence contentDescription;
		@Nullable
		public CharSequence stateDescription;
		public CharSequence dualLabelContentDescription;
		public boolean disabledByPolicy;
		public boolean dualTarget = false;
		public boolean isTransient = false;
		public String expandedAccessibilityClassName;
		public SlashState slash;
		public boolean handlesLongClick = true;
		public boolean showRippleEffect = true;
		@Nullable
		public Drawable sideViewCustomDrawable;
		public String spec;

		public State() {
		}

		public String getStateText(int var1, Resources var2) {
			if (this.state != 0 && !(this instanceof BooleanState)) {
				return "";
			} else {
				String[] var3 = var2.getStringArray(var1);
				return var3[this.state];
			}
		}

		public String getSecondaryLabel(String var1) {
			CharSequence var2 = this.secondaryLabel;
			return TextUtils.isEmpty(var2) ? var1 : var2.toString();
		}

		public boolean copyTo(State var1) {
			if (var1 == null) {
				throw new IllegalArgumentException();
			} else if (!var1.getClass().equals(this.getClass())) {
				throw new IllegalArgumentException();
			} else {
				boolean var2 = !Objects.equals(var1.spec, this.spec) || !Objects.equals(var1.icon, this.icon) || !Objects.equals(var1.iconSupplier, this.iconSupplier) || !Objects.equals(var1.label, this.label) || !Objects.equals(var1.secondaryLabel, this.secondaryLabel) || !Objects.equals(var1.contentDescription, this.contentDescription) || !Objects.equals(var1.stateDescription, this.stateDescription) || !Objects.equals(var1.dualLabelContentDescription, this.dualLabelContentDescription) || !Objects.equals(var1.expandedAccessibilityClassName, this.expandedAccessibilityClassName) || !Objects.equals(var1.disabledByPolicy, this.disabledByPolicy) || !Objects.equals(var1.state, this.state) || !Objects.equals(var1.isTransient, this.isTransient) || !Objects.equals(var1.dualTarget, this.dualTarget) || !Objects.equals(var1.slash, this.slash) || !Objects.equals(var1.handlesLongClick, this.handlesLongClick) || !Objects.equals(var1.showRippleEffect, this.showRippleEffect) || !Objects.equals(var1.sideViewCustomDrawable, this.sideViewCustomDrawable);
				var1.spec = this.spec;
				var1.icon = this.icon;
				var1.iconSupplier = this.iconSupplier;
				var1.label = this.label;
				var1.secondaryLabel = this.secondaryLabel;
				var1.contentDescription = this.contentDescription;
				var1.stateDescription = this.stateDescription;
				var1.dualLabelContentDescription = this.dualLabelContentDescription;
				var1.expandedAccessibilityClassName = this.expandedAccessibilityClassName;
				var1.disabledByPolicy = this.disabledByPolicy;
				var1.state = this.state;
				var1.dualTarget = this.dualTarget;
				var1.isTransient = this.isTransient;
				var1.slash = this.slash != null ? this.slash.copy() : null;
				var1.handlesLongClick = this.handlesLongClick;
				var1.showRippleEffect = this.showRippleEffect;
				var1.sideViewCustomDrawable = this.sideViewCustomDrawable;
				return var2;
			}
		}

		public String toString() {
			return this.toStringBuilder().toString();
		}

		protected StringBuilder toStringBuilder() {
			StringBuilder var1 = (new StringBuilder(this.getClass().getSimpleName())).append('[');
			var1.append("spec=").append(this.spec);
			var1.append(",icon=").append(this.icon);
			var1.append(",iconSupplier=").append(this.iconSupplier);
			var1.append(",label=").append(this.label);
			var1.append(",secondaryLabel=").append(this.secondaryLabel);
			var1.append(",contentDescription=").append(this.contentDescription);
			var1.append(",stateDescription=").append(this.stateDescription);
			var1.append(",dualLabelContentDescription=").append(this.dualLabelContentDescription);
			var1.append(",expandedAccessibilityClassName=").append(this.expandedAccessibilityClassName);
			var1.append(",disabledByPolicy=").append(this.disabledByPolicy);
			var1.append(",dualTarget=").append(this.dualTarget);
			var1.append(",isTransient=").append(this.isTransient);
			var1.append(",state=").append(this.state);
			var1.append(",slash=\"").append(this.slash).append("\"");
			var1.append(",sideViewCustomDrawable=").append(this.sideViewCustomDrawable);
			return var1.append(']');
		}

		public State copy() {
			State var1 = new State();
			this.copyTo(var1);
			return var1;
		}
	}

	public abstract static class Icon {
		public Icon() {
		}

		public abstract Drawable getDrawable(Context var1);

		public Drawable getInvisibleDrawable(Context var1) {
			return this.getDrawable(var1);
		}

		public int hashCode() {
			return Icon.class.hashCode();
		}

		public int getPadding() {
			return 0;
		}

		public String toString() {
			return "Icon";
		}
	}

	public interface Callback {
		void onStateChanged(State var1);
	}
}
