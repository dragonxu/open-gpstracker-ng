/*------------------------------------------------------------------------------
 **     Ident: Sogeti Smart Mobile Solutions
 **    Author: René de Groot
 ** Copyright: (c) 2017 Sogeti Nederland B.V. All Rights Reserved.
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
package nl.sogeti.android.gpstracker.ng.map

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import nl.sogeti.android.gpstracker.ng.utils.DefaultResultHandler
import nl.sogeti.android.gpstracker.ng.utils.ResultHandler
import nl.sogeti.android.gpstracker.ng.utils.readTrack

class TrackReader(private var context: Context, internal val trackUri: Uri, private val action: (String, LatLngBounds, List<List<LatLng>>) -> Unit)
    : AsyncTask<Void, Void, ResultHandler>() {


    var isFinished = false
        private set

    override fun doInBackground(vararg p: Void): ResultHandler? {
        val handler = DefaultResultHandler()
        if (isCancelled) return null
        trackUri.readTrack(context, handler)
        if (isCancelled) return null
        val points = handler.waypoints.map { it.map { it.latLng } }
        val name = handler.name ?: ""
        action(name, handler.bounds, points)
        if (isCancelled) return null

        return handler
    }

    override fun onPostExecute(result: ResultHandler?) {
        super.onPostExecute(result)
        isFinished = true
    }

    override fun onCancelled() {
        super.onCancelled()
        isFinished = true
    }
}
