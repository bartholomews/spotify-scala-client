package io.bartholomews.spotify4s.playJson

import io.bartholomews.spotify4s.core.entities.{ExternalResourceUrl, SimpleArtist, SpotifyId, SpotifyUri}
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{Format, JsPath, JsSuccess, Json, Reads, Writes}

private[spotify4s] object SimpleArtistPlayJson {
  import io.bartholomews.spotify4s.playJson.codecs._
  val reads: Reads[SimpleArtist] =
    (JsPath \ "external_urls")
      .readNullable[ExternalResourceUrl]
      .orElse(_ => JsSuccess(None))
      .and((JsPath \ "href").readNullable[String])
      .and((JsPath \ "id").readNullable[SpotifyId])
      .and((JsPath \ "name").read[String])
      .and((JsPath \ "uri").readNullable[SpotifyUri])(SimpleArtist.apply _)

  val writes: Writes[SimpleArtist] = Json.writes

  val format: Format[SimpleArtist] = Format(reads, writes)
}
