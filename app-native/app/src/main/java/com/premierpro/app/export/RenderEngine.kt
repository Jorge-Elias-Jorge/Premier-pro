package com.premierpro.app.export

import com.premierpro.app.timeline.Clip

class RenderEngine {

    fun prepareRender(
        clips: List<Clip>,
        settings: ExportSettings
    ): RenderJob {
        val duration = clips.sumOf { it.trimEndSeconds - it.trimStartSeconds }
        return RenderJob(
            totalFrames = (duration * settings.frameRate).toInt(),
            settings = settings.normalized(),
            durationSeconds = duration
        )
    }

    fun estimatedFileSizeMb(
        job: RenderJob
    ): Double {
        val baseBitrateMbps = when (job.settings.resolution) {
            ExportResolution.SD_480P -> 2.0
            ExportResolution.HD_720P -> 5.0
            ExportResolution.HD_1080P -> 10.0
            ExportResolution.UHD_4K -> 35.0
        }
        val bitrate = baseBitrateMbps * job.settings.quality.bitrateMultiplier
        return (bitrate * job.durationSeconds / 8.0)
    }

    fun progressAt(
        job: RenderJob,
        renderedFrames: Int
    ): Float {
        if (job.totalFrames <= 0) return 1f
        return (renderedFrames.toFloat() / job.totalFrames)
            .coerceIn(0f, 1f)
    }
}

data class RenderJob(
    val totalFrames: Int,
    val settings: ExportSettings,
    val durationSeconds: Double
)
