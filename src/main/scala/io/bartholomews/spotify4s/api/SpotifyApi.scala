package io.bartholomews.spotify4s.api

import cats.data.NonEmptyList
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.MaxSize
import eu.timepit.refined.numeric.{GreaterEqual, Interval}
import io.bartholomews.spotify4s.config.SpotifyConfig
import io.bartholomews.spotify4s.entities.SpotifyUri

object SpotifyApi {
  private[api] val apiUri = SpotifyConfig.spotify.apiUri.value
  private[api] val accountsUri = SpotifyConfig.spotify.accountsUri.value
  type Limit = Refined[Int, Interval.Closed[1, 50]]
  type Offset = Refined[Int, Interval.Closed[0, 100]]
  type TracksPosition = Refined[Int, GreaterEqual[0]]
  type SpotifyUris = Refined[NonEmptyList[SpotifyUri], MaxSize[100]]
}
