package uz.alisoft.office.ui

import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.alisoft.office.ui.data.DocInfo
import com.cherry.lib.doc.bean.FileType
import com.cherry.lib.doc.util.FileUtils
import uz.alisoft.office.R
import uz.alisoft.office.databinding.RvDocItemCellBinding
import java.io.File

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DocViewHolder
 * Author: Victor
 * Date: 2023/10/26 10:57
 * Description: 
 * -----------------------------------------------------------------
 */

class DocCellViewHolder(
    private val binding: RvDocItemCellBinding,
    groupPosition: Int) : RecyclerView.ViewHolder(binding.root),OnClickListener {
    var mOnItemClickListener: OnItemClickListener? = null
    var parentPosition: Int = 0
    init {
       parentPosition = groupPosition
       binding.root.setOnClickListener(this)
    }

    fun bindData(data: DocInfo?) {
        var typeIcon = data?.getTypeIcon() ?: -1
        if (typeIcon == -1) {
            var file = File(data?.path)
            if (file.exists()) {
                binding.mIvType.load(File(data?.path))
            } else {
                binding.mIvType.load(com.cherry.lib.doc.R.drawable.all_doc_ic)
            }
        } else {
            binding.mIvType.load(typeIcon)
        }
        binding.mTvFileName.text = data?.fileName
        binding.mTvFileDes.text = "${data?.getFileType()} | ${data?.fileSize}\n${data?.lastModified}"

        val type = FileUtils.getFileTypeForUrl(data?.path)
        when (type) {
            FileType.PDF -> {
                binding.mCvDocCell.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        binding.mCvDocCell.resources,
                        R.color.listItemColorPdf,
                        binding.mCvDocCell.context.theme
                    )
                )
            }
            FileType.DOC,FileType.DOCX -> {
                binding.mCvDocCell.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        binding.mCvDocCell.resources,
                        R.color.listItemColorDoc,
                        binding.mCvDocCell.context.theme
                    )
                )
            }
            FileType.XLS,FileType.XLSX -> {
                binding.mCvDocCell.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        binding.mCvDocCell.resources,
                        R.color.listItemColorExcel,
                        binding.mCvDocCell.context.theme
                    )
                )
            }
            FileType.PPT,FileType.PPTX -> {
                binding.mCvDocCell.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        binding.mCvDocCell.resources,
                        R.color.listItemColorPPT,
                        binding.mCvDocCell.context.theme
                    )
                )
            }
            FileType.IMAGE -> {
                binding.mCvDocCell.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        binding.mCvDocCell.resources,
                        R.color.listItemColorImage,
                        binding.mCvDocCell.context.theme
                    )
                )
            }
        }
    }

    override fun onClick(v: View?) {
        mOnItemClickListener?.onItemClick(null,v,adapterPosition,parentPosition.toLong())
    }

}