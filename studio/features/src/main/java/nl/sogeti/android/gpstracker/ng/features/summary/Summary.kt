/*------------------------------------------------------------------------------
 **     Ident: Sogeti Smart Mobile Solutions
 **    Author: rene
 ** Copyright: (c) 2016 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced
 ** Distributed Software Engineering |  or transmitted in any form or by any
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the
 ** 4131 NJ Vianen                   |  purpose, without the express written
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 *
 *   This file is part of OpenGPSTracker.
 *
 *   OpenGPSTracker is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   OpenGPSTracker is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with OpenGPSTracker.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package nl.sogeti.android.gpstracker.ng.features.summary

import android.net.Uri
import com.google.android.gms.maps.model.LatLngBounds
import nl.sogeti.android.gpstracker.ng.features.trackedit.TrackTypeDescriptions
import nl.sogeti.android.gpstracker.service.util.Waypoint

data class Summary(val trackUri: Uri,
                   val name: String,
                   val type: TrackTypeDescriptions.TrackType,
                   val startTimestamp: Long,
                   val stopTimestamp: Long,
                   val trackedPeriod: Long,
                   val distance: Float,
                   val bounds: LatLngBounds,
                   val waypoints: List<List<Waypoint>>,
                   val deltas: List<List<Delta>>) {
    val count: Int
        get() = waypoints.fold(0, { count, list -> count + list.size })

    data class Delta(val totalMilliseconds: Long, val totalMeters: Float, val deltaMeters: Float, val deltaMilliseconds: Long)
}
