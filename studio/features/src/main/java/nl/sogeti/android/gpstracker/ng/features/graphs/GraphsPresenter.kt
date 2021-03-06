/*------------------------------------------------------------------------------
 **     Ident: Sogeti Smart Mobile Solutions
 **    Author: rene
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
package nl.sogeti.android.gpstracker.ng.features.graphs

import android.net.Uri
import nl.sogeti.android.gpstracker.ng.base.common.ofMainThread
import nl.sogeti.android.gpstracker.ng.base.common.postMainThread
import nl.sogeti.android.gpstracker.ng.features.FeatureConfiguration
import nl.sogeti.android.gpstracker.ng.features.graphs.dataproviders.DistanceDataProvider
import nl.sogeti.android.gpstracker.ng.features.graphs.dataproviders.GraphDataCalculator
import nl.sogeti.android.gpstracker.ng.features.graphs.dataproviders.TimeDataProvider
import nl.sogeti.android.gpstracker.ng.features.summary.Summary
import nl.sogeti.android.gpstracker.ng.features.summary.SummaryManager
import nl.sogeti.android.gpstracker.ng.features.util.AbstractSelectedTrackPresenter
import javax.inject.Inject

class GraphsPresenter : AbstractSelectedTrackPresenter() {

    @Inject
    lateinit var summaryManager: SummaryManager
    internal val viewModel = GraphsViewModel()
    private var graphDataProvider: GraphDataCalculator = TimeDataProvider(viewModel.inverseSpeed.get())
    private var trackSummary: Summary? = null
    private var runningSelection = false

    init {
        FeatureConfiguration.featureComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        summaryManager.start()
    }

    override fun onStop() {
        summaryManager.stop()
        super.onStop()
    }

    //region View callbacks

    fun didSelectDistance() {
        if (runningSelection || viewModel.distanceSelected.get())
            return
        runningSelection = true
        viewModel.distanceSelected.set(true)
        viewModel.durationSelected.set(false)
        ofMainThread {
            trackSummary?.let {
                setSummary(it)
            }
            postMainThread {
                runningSelection = false
            }
        }
    }

    fun didSelectTime() {
        if (runningSelection || viewModel.durationSelected.get())
            return
        runningSelection = true
        viewModel.distanceSelected.set(false)
        viewModel.durationSelected.set(true)
        ofMainThread {
            trackSummary?.let {
                setSummary(it)
            }
            postMainThread {
                runningSelection = false
            }
        }
    }

    //endregion

    //region update

    private fun resetTrack() {
        viewModel.distance.set(0F)
        viewModel.timeSpan.set(0L)
        viewModel.speed.set(0F)
        viewModel.waypoints.set("-")
        viewModel.startTime.set(0L)
        viewModel.duration.set(0L)
        viewModel.paused.set(0L)
    }

    override fun onTrackUpdate(trackUri: Uri?, name: String) {
        viewModel.trackUri.set(trackUri)
        if (trackUri != null) {
            summaryManager.collectSummaryInfo(trackUri) {
                trackSummary = it
                fillSummaryNumbers(it)
                setSummary(it)
            }
        } else {
            resetTrack()
        }
    }

    private fun fillSummaryNumbers(summary: Summary) {
        viewModel.waypoints.set(summary.count.toString())
        viewModel.startTime.set(summary.startTimestamp)
        val total = summary.stopTimestamp - summary.startTimestamp
        val pausedTime = total - summary.trackedPeriod
        viewModel.paused.set(pausedTime)
        viewModel.distance.set(summary.distance)
        viewModel.duration.set(summary.trackedPeriod)
        viewModel.timeSpan.set(total)

        val seconds = summary.trackedPeriod / 1000F
        val speed = if (seconds > 0) summary.distance / seconds else 0F
        viewModel.speed.set(speed)

        viewModel.inverseSpeed.set(summary.type.isRunning())
    }

    private fun setSummary(summary: Summary) {
        val inverseSpeed = summary.type.isRunning()
        if (viewModel.distanceSelected.get()) {
            graphDataProvider = DistanceDataProvider(inverseSpeed)
        } else if (viewModel.durationSelected.get()) {
            graphDataProvider = TimeDataProvider(inverseSpeed)
        }
        viewModel.graphData.set(graphDataProvider.calculateGraphPoints(summary))
        viewModel.xLabel.set(graphDataProvider.xLabel)
        viewModel.yLabel.set(graphDataProvider.yLabel)
        viewModel.graphLabels.set(graphDataProvider)
    }
}

