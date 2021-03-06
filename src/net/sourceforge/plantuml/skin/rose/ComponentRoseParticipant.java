/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.skin.rose;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.SymbolContext;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.skin.AbstractTextualComponent;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class ComponentRoseParticipant extends AbstractTextualComponent {

	private final HtmlColor back;
	private final HtmlColor foregroundColor;
	private final double deltaShadow;
	private final double roundCorner;
	private final UStroke stroke;
	private final double minWidth;
	private final boolean collections;

	public ComponentRoseParticipant(SymbolContext biColor, FontConfiguration font, Display stringsToDisplay,
			ISkinSimple spriteContainer, double roundCorner, UFont fontForStereotype, HtmlColor htmlColorForStereotype,
			double minWidth, boolean collections) {
		super(stringsToDisplay, font, HorizontalAlignment.CENTER, 7, 7, 7, spriteContainer, 0, false,
				fontForStereotype, htmlColorForStereotype);
		this.minWidth = minWidth;
		this.collections = collections;
		this.back = biColor.getBackColor();
		this.roundCorner = roundCorner;
		this.deltaShadow = biColor.getDeltaShadow();
		this.foregroundColor = biColor.getForeColor();
		this.stroke = biColor.getStroke();
	}

	@Override
	protected void drawInternalU(UGraphic ug, Area area) {
		final StringBounder stringBounder = ug.getStringBounder();
		ug = ug.apply(new UChangeBackColor(back)).apply(new UChangeColor(foregroundColor));
		ug = ug.apply(stroke);
		final URectangle rect = new URectangle(getTextWidth(stringBounder), getTextHeight(stringBounder), roundCorner,
				roundCorner);
		rect.setDeltaShadow(deltaShadow);
		if (collections) {
			ug.apply(new UTranslate(getDeltaCollection(), 0)).draw(rect);
			ug = ug.apply(new UTranslate(0, getDeltaCollection()));
		}
		ug.draw(rect);
		ug = ug.apply(new UStroke());
		final TextBlock textBlock = getTextBlock();
		textBlock.drawU(ug.apply(new UTranslate(getMarginX1() + suppWidth(stringBounder) / 2, getMarginY())));
	}

	private double getDeltaCollection() {
		if (collections) {
			return 4;
		}
		return 0;
	}

	@Override
	public double getPreferredHeight(StringBounder stringBounder) {
		return getTextHeight(stringBounder) + deltaShadow + 1 + getDeltaCollection();
	}

	@Override
	public double getPreferredWidth(StringBounder stringBounder) {
		return getTextWidth(stringBounder) + deltaShadow + getDeltaCollection();
	}

	@Override
	protected double getPureTextWidth(StringBounder stringBounder) {
		return Math.max(super.getPureTextWidth(stringBounder), minWidth);
	}

	private final double suppWidth(StringBounder stringBounder) {
		return getPureTextWidth(stringBounder) - super.getPureTextWidth(stringBounder);
	}

}
