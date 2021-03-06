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
package nl.sogeti.android.gpstracker.ng.features.databinding

import androidx.databinding.DataBindingComponent
import nl.sogeti.android.gpstracker.ng.features.control.ControlBindingAdapters
import nl.sogeti.android.gpstracker.ng.features.graphs.GraphLabelsBindings
import nl.sogeti.android.gpstracker.ng.features.graphs.widgets.LineGraphBindings
import nl.sogeti.android.gpstracker.ng.features.map.bindings.MapBindingAdapters
import nl.sogeti.android.gpstracker.ng.features.recording.RecordingBindingAdapters
import nl.sogeti.android.gpstracker.ng.features.trackedit.TrackTypesBindingAdapters
import nl.sogeti.android.gpstracker.ng.features.tracklist.TracksBindingAdapters

class FeaturesBindingComponent : DataBindingComponent {

    override fun getGraphLabelsBindings() = GraphLabelsBindings()

    override fun getMapBindingAdapters(): MapBindingAdapters = MapBindingAdapters()

    override fun getLineGraphBindings(): LineGraphBindings = LineGraphBindings()

    override fun getCommonBindingAdapters(): CommonBindingAdapters = CommonBindingAdapters()

    override fun getRecordingBindingAdapters(): RecordingBindingAdapters =
            RecordingBindingAdapters()

    override fun getControlBindingAdapters(): ControlBindingAdapters = ControlBindingAdapters()

    override fun getTracksBindingAdapters(): TracksBindingAdapters = TracksBindingAdapters()

    override fun getTrackTypesBindingAdapters(): TrackTypesBindingAdapters =
            TrackTypesBindingAdapters()
}
