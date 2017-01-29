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
package nl.sogeti.android.gpstracker.ng.util

import android.content.Context
import nl.sogeti.android.gpstracker.integration.ServiceConstants.*
import nl.sogeti.android.gpstracker.integration.ServiceManagerInterface

class MockServiceManager : ServiceManagerInterface {

    val broadcaster = MockBroadcastSender()
    val gpsProvider = Recorder()

    override fun startup(context: Context, runnable: Runnable?) {
        globalState.started = true
        if (globalState.loggingState == STATE_UNKNOWN) {
            globalState.loggingState = STATE_STOPPED
        }
        runnable?.run()
    }

    override fun shutdown(context: Context) {
        globalState.started = false
    }

    override fun getLoggingState(): Int = globalState.loggingState

    override fun getTrackId(): Long = globalState.trackId

    override fun startGPSLogging(context: Context, trackName: String?) {
        globalState.loggingState = STATE_LOGGING
        globalState.trackId++

        broadcaster.sendStartedRecording(context, trackId)
        gpsProvider.addTrack()
    }

    override fun stopGPSLogging(context: Context) {
        globalState.loggingState = STATE_STOPPED

        broadcaster.sendStoppedRecording(context)
    }

    override fun pauseGPSLogging(context: Context) {
        globalState.loggingState = STATE_PAUSED

        broadcaster.sendPausedRecording(context, trackId)
    }

    override fun resumeGPSLogging(context: Context) {
        globalState.loggingState = STATE_LOGGING

        broadcaster.sendResumedRecording(context, trackId)
    }

    fun reset() {
        started = false
        globalState.loggingState = STATE_UNKNOWN
        globalState.trackId = 5L
    }

    companion object globalState {
        var started = false
        var loggingState = STATE_UNKNOWN
        var trackId = 5L
        var segmentId = 10L
        var waypointId = 100L
    }

    class Recorder {

        fun addTrack() {
            trackId++
            MockTracksProvider.globalState.addTrack(trackId)
            addSegment()
        }

        fun addSegment() {
            segmentId++
            MockTracksProvider.globalState.addSegment(trackId, segmentId)
            addWaypoint()
        }

        fun addWaypoint() {
            waypointId++
            val amplitude = 1.0 + waypointId / 360.0
            val latitude = 52.0 + amplitude * Math.cos(Math.toRadians(waypointId.toDouble()))
            val longitude = 5.0 + amplitude * Math.sin(Math.toRadians(waypointId.toDouble()))
            MockTracksProvider.globalState.addWaypoint(trackId, segmentId, waypointId, latitude, longitude)
        }
    }
}
