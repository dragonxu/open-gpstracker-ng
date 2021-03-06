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
package nl.sogeti.android.gpstracker.ng.base.dagger

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import dagger.Component
import nl.sogeti.android.gpstracker.ng.base.location.LocationFactory
import nl.sogeti.android.gpstracker.ng.common.controllers.gpsstatus.GpsStatusControllerFactory
import java.text.SimpleDateFormat
import java.util.concurrent.Executor
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SystemModule::class])
interface AppComponent {

    fun gpsStatusControllerFactory(): GpsStatusControllerFactory

    @Named("dayFormatter")
    fun dayFormatter(): SimpleDateFormat

    @DiskIO
    fun diskExecutor(): Executor

    @NetworkIO
    fun networkExecutor(): Executor

    @Computation
    fun computationExecutor(): Executor

    fun applicationContext(): Context

    fun locationFactory(): LocationFactory

    fun uriBuilder(): Uri.Builder

    fun contentResolver(): ContentResolver

    fun packageManager(): PackageManager
}
