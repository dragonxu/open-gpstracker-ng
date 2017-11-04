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
package nl.sogeti.android.gpstracker.v2.wear

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nl.sogeti.android.gpstracker.v2.wear.databinding.ControlBindingComponent
import nl.sogeti.android.gpstracker.v2.wear.databinding.ItemControlBinding

class ControlsAdapter(private val model: ControlViewModel, private val presenter: ControlPresenter?) : RecyclerView.Adapter<ControlViewHolder>() {

    init {
        model.controls.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Control>>() {
            override fun onChanged(sender: ObservableList<Control>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<Control>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<Control>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<Control>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeChanged(sender: ObservableList<Control>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ControlViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<ItemControlBinding>(inflater, R.layout.item_control, parent, false, ControlBindingComponent())

        return ControlViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ControlViewHolder, position: Int) {
        holder.binding.viewModel = model.controls[position]
        holder.binding.presenter = presenter
    }

    override fun getItemCount() = model.controls.count()

}

class ControlViewHolder(val binding: ItemControlBinding) : RecyclerView.ViewHolder(binding.root)
