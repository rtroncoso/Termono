package com.quest.pools;

import java.util.List;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.debug.Debug;

import com.quest.entities.Mob;
import com.quest.game.Game;

public class TextPool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final MultiPool<Text> mTextMultiPool = new MultiPool<Text>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void registerText(final int TEXT_TYPE_FLAG, final Font pFont) {
		
		this.mTextMultiPool.registerPool(TEXT_TYPE_FLAG,
				new GenericPool<Text>() {
					@Override
					protected Text onAllocatePoolItem() {
						try {
							return new Text(0, 0, pFont, "Text",256, Game.getInstance().getVertexBufferObjectManager());
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
					}
					
					@Override
					protected void onHandleRecycleItem(final Text pText) {
						pText.setVisible(false);
						pText.setAlpha(1f);
						pText.setUserData(null);
						pText.setCullingEnabled(true);
						pText.setPosition(-10, -10);
						pText.setScale(1);
						pText.detachSelf();
					}
					
					@Override
					protected void onHandleObtainItem(Text pText) {
						pText.setVisible(true);
						pText.setTag(TEXT_TYPE_FLAG);
						pText.setText("");
					};
				}
		);
	}

	
	public Text obtainText(final int TEXT_TYPE) {
		return this.mTextMultiPool.obtainPoolItem(TEXT_TYPE);
	}

	
	public void recycleText(final Text pText) {
		this.mTextMultiPool.recyclePoolItem(pText.getTag(),pText);
	}

	public void recycleTexts(final List<Text> pTexts) {
		final MultiPool<Text> textMultiPool = this.mTextMultiPool;
		for(int i = pTexts.size() - 1; i >= 0; i--) {
			final Text tmpText = pTexts.get(i);
			textMultiPool.recyclePoolItem(tmpText.getTag(), tmpText);
		}
	}

	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
