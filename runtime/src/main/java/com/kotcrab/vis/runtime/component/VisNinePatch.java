
package com.kotcrab.vis.runtime.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.runtime.util.autotable.ATProperty;
import com.kotcrab.vis.runtime.util.autotable.ATUseGetterSetter;

public class VisNinePatch extends Component {

	@ATProperty(fieldName = "EdgeLeft", min = 0, tooltip = "Pixels from left edge.") @ATUseGetterSetter private int left;
	@ATProperty(fieldName = "EdgeRight", min = 0, tooltip = "Pixels from right edge.") @ATUseGetterSetter private int right;
	@ATProperty(fieldName = "EdgeTop", min = 0, tooltip = "Pixels from top edge.") @ATUseGetterSetter private int top;
	@ATProperty(fieldName = "EdgeBottom", min = 0, tooltip = "Pixels from bottom edge.") @ATUseGetterSetter private int bottom;

	private NinePatch ninePatch;
	private Sprite sprite;
	private transient boolean dirty = true;

	public VisNinePatch () {

	}

	public VisNinePatch (VisNinePatch other) {
		this.left = other.left;
		this.right = other.right;
		this.top = other.top;
		this.bottom = other.bottom;
	}

	public int getLeft () {
		return left;
	}

	public void setLeft (int left) {
		this.dirty = true;
		this.left = left;
	}

	public int getRight () {
		return right;
	}

	public void setRight (int right) {
		this.dirty = true;
		this.right = right;
	}

	public int getTop () {
		return top;
	}

	public void setTop (int top) {
		this.dirty = true;
		this.top = top;
	}

	public int getBottom () {
		return bottom;
	}

	public void setBottom (int bottom) {
		this.dirty=true;
		this.bottom = bottom;
	}

	public NinePatch getNinePatch () {
		return ninePatch;
	}

	public void setNinePatch (NinePatch ninePatch) {
		this.ninePatch = ninePatch;
	}

	public void setTexture (TextureRegion texture, float pixelsPerUnit) {
		this.ninePatch = new NinePatch(texture, left, right, top, bottom);
		float scale=1/pixelsPerUnit;
		this.ninePatch.scale(scale,scale);
		this.sprite = new Sprite(texture);
	}

	public boolean isDirty () {
		return dirty;
	}

	public void setDirty (boolean dirty) {
		this.dirty = dirty;
	}

	public Rectangle getBoundingRectangle (Transform transform,Origin origin) {
		if(this.sprite==null) this.sprite=new Sprite();
		sprite.setX(transform.getX());
		sprite.setY(transform.getY());
		sprite.setScale(transform.getScaleX(),transform.getScaleY());
		sprite.rotate(transform.getRotation());
		if(origin!=null){
			sprite.setOrigin(origin.getOriginX(), origin.getOriginY());
		}		
		return sprite.getBoundingRectangle();
	}
}
