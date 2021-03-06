package org.droidplanner.services.android.core.survey.grid;

import java.util.ArrayList;
import java.util.List;

import org.droidplanner.services.android.core.helpers.coordinates.Coord2D;
import org.droidplanner.services.android.core.helpers.geoTools.LineCoord2D;
import org.droidplanner.services.android.core.helpers.geoTools.LineTools;

public class Trimmer {
	List<LineCoord2D> trimedGrid = new ArrayList<LineCoord2D>();

	public Trimmer(List<LineCoord2D> grid, List<LineCoord2D> polygon) {
		for (LineCoord2D gridLine : grid) {
			ArrayList<Coord2D> crosses = findCrossings(polygon, gridLine);
			processCrossings(crosses, gridLine);
		}
	}

	private ArrayList<Coord2D> findCrossings(List<LineCoord2D> polygon, LineCoord2D gridLine) {

		ArrayList<Coord2D> crossings = new ArrayList<Coord2D>();
		for (LineCoord2D polyLine : polygon) {
            Coord2D intersection = LineTools.FindLineIntersection(polyLine, gridLine);
            if(intersection != null)
                crossings.add(intersection);
		}

		return crossings;
	}

	private void processCrossings(ArrayList<Coord2D> crosses, LineCoord2D gridLine) {
		switch (crosses.size()) {
		case 0:
		case 1:
			break;
		case 2:
			trimedGrid.add(new LineCoord2D(crosses.get(0), crosses.get(1)));
			break;
		default: // TODO handle multiple crossings in a better way
			trimedGrid.add(LineTools.findExternalPoints(crosses));
		}
	}

	public List<LineCoord2D> getTrimmedGrid() {
		return trimedGrid;
	}

}
