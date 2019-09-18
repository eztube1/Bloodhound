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

import java.util.concurrent.TimeUnit
import com.apifortress.afthem.actors.AppContext
import com.apifortress.afthem.config.ApplicationConf
import org.apache.http.HttpResponse
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.concurrent.FutureCallback
import org.apache.http.impl.nio.client.{CloseableHttpAsyncClient, HttpAsyncClients}
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager
import org.apache.http.impl.nio.reactor.{DefaultConnectingIOReactor, IOReactorConfig}

/**
  * A shared asynchronous HTTP client for all Afthem needs
  */
object AfthemHttpClient {

  private val applicationConf = AppContext.springApplicationContext.getBean(classOf[ApplicationConf])
  private val maxThreads : Int = if(AppContext.springApplicationContext!=null)
                                    applicationConf.httpClientMaxThreads
                                  else
                                      1
  private val maxConnections : Int = if(AppContext.springApplicationContext!=null)
                                          applicationConf.httpClientMaxConnections
                                        else
                                          100
  private val idleTimeoutSeconds : Int = if(AppContext.springApplicationContext!=null)
                                          applicationConf.httpClientIdleTimeoutSeconds
                                        else
                                           5

  private val reactorConfig = IOReactorConfig.custom().setIoThreadCount(maxThreads).build()
  private val ioReactor = new DefaultConnectingIOReactor(reactorConfig)
  private val connectionManager = new PoolingNHttpClientConnectionManager(ioReactor)

  connectionManager.setMaxTotal(maxConnections)

  private val requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                                        .setSocketTimeout(10000)
                                        .setRedirectsEnabled(true)
                                        .setMaxRedirects(5).build()

  val httpClient : CloseableHttpAsyncClient = HttpAsyncClients.custom().disableCookieManagement()
                                                .setConnectionManager(connectionManager)
                                                .setDefaultRequestConfig(requestConfig).build()

  httpClient.start()

  /**
    * Executes an asynchronous HTTP call
    * @param request the request
    * @param callback the callback to be called when the response is ready
    */
  def execute(request : HttpUriRequest, callback : FutureCallback[HttpResponse]): Unit ={
    httpClient.execute(request,callback)
  }

  def closeStaleConnections() : Unit = {
    connectionManager.closeExpiredConnections()
    connectionManager.closeIdleConnections(5, TimeUnit.SECONDS)
  }
}