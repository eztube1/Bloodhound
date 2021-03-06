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
package com.apifortress.afthem

import com.apifortress.afthem.messages.beans.{Header, HttpWrapper}

object TestData {
  def createWrapper() : HttpWrapper =
    new HttpWrapper("http://foo.com",200,"GET",
      List[Header](new Header(ReqResUtil.HEADER_CONTENT_LENGTH,"123"),
        new Header(ReqResUtil.HEADER_CONTENT_ENCODING,"gzip"),
        new Header("x-key","ABC123")))
}
