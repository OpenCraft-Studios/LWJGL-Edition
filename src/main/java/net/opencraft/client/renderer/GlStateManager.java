package net.opencraft.client.renderer;

import org.lwjgl.opengl.GL11;

public class GlStateManager {
	
	private static final GlStateManager.CullState CULL = new GlStateManager.CullState();

	public static void enableCull() {
		CULL.cullFace.setEnabled();
	}

	public static void disableCull() {
		CULL.cullFace.setDisabled();
	}

	static class BooleanState {
		private final int capability;
		private boolean currentState;

		public BooleanState(int capabilityIn) {
			this.capability = capabilityIn;
		}

		public void setDisabled() {
			this.setState(false);
		}

		public void setEnabled() {
			this.setState(true);
		}

		public void setState(boolean state) {
			if (state != this.currentState) {
				this.currentState = state;
				if (state) {
					GL11.glEnable(this.capability);
				} else {
					GL11.glDisable(this.capability);
				}
			}

		}
	}

	static class CullState {
		public GlStateManager.BooleanState cullFace = new GlStateManager.BooleanState(2884);
		public int mode = 1029;

		private CullState() {
		}
	}

}
