/*
 * Copyright 2014-2016 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.vis.runtime.system.render;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.runtime.component.Invisible;
import com.kotcrab.vis.runtime.component.Origin;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisNinePatch;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.delegate.DeferredEntityProcessingSystem;
import com.kotcrab.vis.runtime.system.delegate.EntityProcessPrincipal;

/**
 * Renders entities with {@link VisSprite}.
 * @author Kotcrab
 */
public class SpriteRenderSystem extends DeferredEntityProcessingSystem {
	private ComponentMapper<VisSprite> spriteCm;
	private ComponentMapper<Transform> transformCm;
	private ComponentMapper<Origin> originCm;
	private ComponentMapper<VisNinePatch> nineCm;

	private RenderBatchingSystem renderSystem;
	private Batch batch;
	private float pixelsPerUnit;

	public SpriteRenderSystem (EntityProcessPrincipal principal, float pixelsPerUnit) {
		super(Aspect.all(VisSprite.class).exclude(Invisible.class), principal);
		this.pixelsPerUnit = pixelsPerUnit;
	}

	@Override
	protected void initialize () {
		batch = renderSystem.getBatch();
	}

	@Override
	protected void process (int entityId) {
		VisSprite sprite = spriteCm.get(entityId);
		Transform transform = transformCm.get(entityId);
		Origin origin = originCm.get(entityId);
		VisNinePatch nine = nineCm.get(entityId);
		if(nine!=null && (nine.getLeft()!=0 || nine.getRight()!=0 || nine.getTop()!=0 || nine.getBottom()!=0) ){
			if(nine.isDirty()){
				nine.setTexture(sprite.getBaseRegion(),pixelsPerUnit);
				nine.setDirty(false);
				
			}
			Rectangle bound = nine.getBoundingRectangle(transform, origin);
			nine.getNinePatch().draw(batch,bound.x,bound.y,bound.width/pixelsPerUnit,bound.height/pixelsPerUnit);
		}else{
		   batch.draw(sprite.getRegion(),transform.getX(), transform.getY(), origin.getOriginX(), origin.getOriginY(),
			  	  sprite.getWidth(), sprite.getHeight(), transform.getScaleX(), transform.getScaleY(), transform.getRotation());
		}
	}
}
