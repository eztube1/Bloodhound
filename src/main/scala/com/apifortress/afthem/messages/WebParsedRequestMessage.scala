package com.apifortress.afthem.messages

import com.apifortress.afthem.config.Backend
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import java.util.Date

import scala.collection.mutable

/**
  * Copyright 2019 API Fortress
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  * @author Simone Pezzano
  */
case class WebParsedRequestMessage(request: HttpWrapper,
                                   deferredResult: DeferredResult[ResponseEntity[Array[Byte]]],
                                   override val date: Date, override val meta: mutable.HashMap[String,Any],
                                   var backendConfig: Backend = null) extends BaseMessage(date,meta)
