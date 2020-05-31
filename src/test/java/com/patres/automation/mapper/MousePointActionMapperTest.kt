package com.patres.automation.mapper

import com.patres.automation.action.mouse.point.ImagePointDetector
import com.patres.automation.action.mouse.point.SpecificPointDetector
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.point.Point
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.util.Base64Converter
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeTypeOf
import javafx.beans.property.SimpleBooleanProperty


class MousePointActionMapperTest : JfxSpec({

    val testedPointX = 12
    val testedPointY = 34
    val testedPoint = "($testedPointX;$testedPointY)"
    val testedThreshold = 0.78
    val testedImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAC4jAAAuIwF4pT92AAAKqWlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIiB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTAzLTE1VDE3OjI1OjExKzAxOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIwLTAzLTE1VDE5OjMyOjU0KzAxOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wMy0xNVQxOTozMjo1NCswMTowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Y2MwOTdkOWUtYWZmNC04MDQ4LWJiN2YtM2M1M2RlNjJiNWY5IiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6MTk0Mzg5YzYtZWMwNC04NzRlLWJmN2MtYTI2YTE5MzNhMTA2IiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6NDU2Njc3OTUtYzQ5YS0wNjRjLTk2MmYtZTdlNDEyYmVmMzllIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHRpZmY6T3JpZW50YXRpb249IjEiIHRpZmY6WFJlc29sdXRpb249IjMwMDAwMDAvMTAwMDAiIHRpZmY6WVJlc29sdXRpb249IjMwMDAwMDAvMTAwMDAiIHRpZmY6UmVzb2x1dGlvblVuaXQ9IjIiIGV4aWY6Q29sb3JTcGFjZT0iMSIgZXhpZjpQaXhlbFhEaW1lbnNpb249IjUxMiIgZXhpZjpQaXhlbFlEaW1lbnNpb249IjUxMiI+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NDU2Njc3OTUtYzQ5YS0wNjRjLTk2MmYtZTdlNDEyYmVmMzllIiBzdEV2dDp3aGVuPSIyMDIwLTAzLTE1VDE3OjI1OjExKzAxOjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOmQxNjY0YjgxLTU0YmQtZGY0Ny1iZjE1LTc0ODkxMjQxYWQ4NSIgc3RFdnQ6d2hlbj0iMjAyMC0wMy0xNVQxNzozNjoyOSswMTowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDplZGRlZTE5OS1kZWU3LTQ5NGUtOTA2Yi1lZWYwNjUwNmNkY2EiIHN0RXZ0OndoZW49IjIwMjAtMDMtMTVUMTk6MzI6NTQrMDE6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGFwcGxpY2F0aW9uL3ZuZC5hZG9iZS5waG90b3Nob3AgdG8gaW1hZ2UvcG5nIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJkZXJpdmVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJjb252ZXJ0ZWQgZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6Y2MwOTdkOWUtYWZmNC04MDQ4LWJiN2YtM2M1M2RlNjJiNWY5IiBzdEV2dDp3aGVuPSIyMDIwLTAzLTE1VDE5OjMyOjU0KzAxOjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmVkZGVlMTk5LWRlZTctNDk0ZS05MDZiLWVlZjA2NTA2Y2RjYSIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOjUwOTI4MzliLThiMmItMWY0Yy1hOGIyLTU5ZjExYzhkZDg4ZCIgc3RSZWY6b3JpZ2luYWxEb2N1bWVudElEPSJ4bXAuZGlkOjQ1NjY3Nzk1LWM0OWEtMDY0Yy05NjJmLWU3ZTQxMmJlZjM5ZSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PtEkw48AAAFESURBVDiNrZO/S0JxFMWPT3vREgYRtTQ1mLpEWxCt+g/U0OpWW2NbQ1lDNCgEDe6uDSXl3Jz9bniQSIFCQ0mWvz4tz3o9fyF04Azfc889fOHeK0AuBoF94A74tHlra0G33/kwgQTQoDsawCEw4g4wgbMejW5k7Z6fgOQAzS0kWgEhoO6sRNMWV8VKv4A6EDIkxSR55UDGetdc6lGxk4JeyjV1gVdSzJAU6VRtIqVyrwocPWj7oqhKvdnJFvEAX5LMP9G7uTbn9OiQdpamtBL0y/MrV41u/3Mj/1bT6nFeG9lnp1z1SbIkBfoFzIyZ2lqc1PKs3yk/+SSd9grwD3u1uTCh9flxmV6Pu3wuIOweoxG/xNzLsZYpUPqo9RpjuOMiRdMWN6W+e5DkP1d54GMy4pdt19hiCDgA7oEqUAaubS3s9n8D/QVrtUYL8toAAAAASUVORK5CYII="

    "Should map serialized to controller" - {
        "with point" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val serializedModel = MousePointActionSerialized(verifiedAction, testedPoint)

                    // when
                    val controller = MousePointActionMapper.serializedToController(serializedModel)

                    // then
                    controller.shouldNotBeNullAndCheck {
                        actionBoot shouldBe verifiedAction
                        image shouldBe null
                        value shouldBe testedPoint
                        valueText.isVisible shouldBe true
                        thresholdSlider.isVisible shouldBe false
                        imageBox.isVisible shouldBe false
                    }
                }
            }
        }
        "with image" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val serializedModel = MousePointActionSerialized(verifiedAction, null, testedImageBase64, testedThreshold)

                    // when
                    val controller = MousePointActionMapper.serializedToController(serializedModel)

                    // then
                    controller.shouldNotBeNullAndCheck {
                        actionBoot shouldBe verifiedAction
                        image shouldNotBe null
                        value shouldBe ""
                        valueText.isVisible shouldBe false
                        thresholdSlider.isVisible shouldBe true
                        imageBox.isVisible shouldBe true
                        thresholdSlider.value shouldBe testedThreshold
                    }
                }
            }
        }
    }

    "Should map serialized to model" - {
        "with point" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val serializedModel = MousePointActionSerialized(verifiedAction, testedPoint)

                    // when
                    val model = MousePointActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false))

                    // then
                    model.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        pointDetector.shouldBeTypeOf<SpecificPointDetector>()
                        pointDetector.calculatePoint() shouldBe Point(testedPointX, testedPointY)
                    }
                }
            }
        }
        "with image" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val serializedModel = MousePointActionSerialized(verifiedAction, null, testedImageBase64, testedThreshold)

                    // when
                    val model = MousePointActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false))

                    // then
                    model.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        pointDetector.shouldBeTypeOf<ImagePointDetector>()
                    }
                }
            }
        }
    }

    "Should map controller to serialized" - {
        "with point" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val controller = MousePointActionController(verifiedAction).apply {
                        value = testedPoint
                    }

                    // when
                    val serialized = MousePointActionMapper.controllerToSerialized(controller)

                    // then
                    serialized.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        point shouldBe testedPoint
                        image shouldBe null
                    }
                }
            }
        }
        "with image" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val controller = MousePointActionController(verifiedAction).apply {
                        setImage(Base64Converter.base64ToInputStream(testedImageBase64))
                        thresholdSlider.value = testedThreshold
                    }

                    // when
                    val serialized = MousePointActionMapper.controllerToSerialized(controller)

                    // then
                    serialized.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        point shouldBe ""
                        image shouldNotBe null
                        threshold shouldBe testedThreshold
                    }
                }
            }
        }
    }

    "Should map controller to model" - {
        "with point" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val controller = MousePointActionController(verifiedAction).apply {
                        value = testedPoint
                    }

                    // when
                    val model = MousePointActionMapper.controllerToModel(controller)

                    // then
                    model.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        pointDetector.shouldBeTypeOf<SpecificPointDetector>()
                        pointDetector.calculatePoint() shouldBe Point(testedPointX, testedPointY)
                    }
                }
            }
        }
        "with image" - {
            ActionBootMousePoint.values().map { verifiedAction: ActionBootMousePoint ->
                verifiedAction.name {
                    // given
                    val controller = MousePointActionController(verifiedAction).apply {
                        setImage(Base64Converter.base64ToInputStream(testedImageBase64))
                        thresholdSlider.value = testedThreshold
                    }

                    // when
                    val model = MousePointActionMapper.controllerToModel(controller)

                    // then
                    model.shouldNotBeNullAndCheck {
                        actionBootType shouldBe verifiedAction
                        pointDetector.shouldBeTypeOf<ImagePointDetector>()
                    }
                }
            }
        }
    }

})

