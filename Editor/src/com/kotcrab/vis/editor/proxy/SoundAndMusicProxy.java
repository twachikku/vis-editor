/*
 * Copyright 2014-2015 See AUTHORS file.
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

package com.kotcrab.vis.editor.proxy;

import com.artemis.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.editor.module.scene.SoundAndMusicRenderSystem;
import com.kotcrab.vis.runtime.assets.PathAsset;
import com.kotcrab.vis.runtime.assets.VisAssetDescriptor;
import com.kotcrab.vis.editor.entity.PositionComponent;
import com.kotcrab.vis.runtime.accessor.BasicPropertiesAccessor;

/** @author Kotcrab */
public class SoundAndMusicProxy extends EntityProxy {
	private PositionComponent pos;
	private final boolean music;

	public SoundAndMusicProxy (Entity entity, boolean music) {
		super(entity);
		this.music = music;
		pos = entity.getComponent(PositionComponent.class);
	}

	@Override
	protected BasicPropertiesAccessor initAccessors () {
		return new Accessor();
	}

	@Override
	protected String getEntityName () {
		return music ? "MusicEntity" : "SoundEntity";
	}

	@Override
	boolean isAssetsDescriptorSupported (VisAssetDescriptor assetDescriptor) {
		return assetDescriptor instanceof PathAsset;
	}

	private class Accessor implements BasicPropertiesAccessor {
		private Rectangle bounds = new Rectangle();

		public Accessor () {
			bounds = new Rectangle(0, 0, SoundAndMusicRenderSystem.ICON_SIZE, SoundAndMusicRenderSystem.ICON_SIZE);
		}

		@Override
		public float getX () {
			return pos.x;
		}

		@Override
		public void setX (float x) {
			pos.x = x;
		}

		@Override
		public float getY () {
			return pos.y;
		}

		@Override
		public void setY (float y) {
			pos.y = y;
		}

		@Override
		public void setPosition (float x, float y) {
			pos.x = x;
			pos.y = y;
		}

		@Override
		public float getWidth () {
			return SoundAndMusicRenderSystem.ICON_SIZE;
		}

		@Override
		public float getHeight () {
			return SoundAndMusicRenderSystem.ICON_SIZE;
		}

		@Override
		public Rectangle getBoundingRectangle () {
			bounds.setPosition(pos.x, pos.y);
			return bounds;
		}
	}
}