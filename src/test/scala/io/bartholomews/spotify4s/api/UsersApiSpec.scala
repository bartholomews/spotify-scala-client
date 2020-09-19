package io.bartholomews.spotify4s.api

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import io.bartholomews.fsclient.entities.FsResponse
import io.bartholomews.fsclient.entities.oauth.NonRefreshableToken
import io.bartholomews.spotify4s.client.ClientData.sampleClient
import io.bartholomews.spotify4s.entities.SpotifyUserId
import io.bartholomews.scalatestudo.WireWordSpec
import io.bartholomews.scalatestudo.data.TestudoFsClientData.OAuthV2

class UsersApiSpec extends WireWordSpec with ServerBehaviours {
  implicit val signer: NonRefreshableToken = OAuthV2.sampleNonRefreshableToken

  "`me`" when {
    def endpoint: MappingBuilder = get(urlPathEqualTo(s"$basePath/me"))

    "successfully authenticated" should {
      val request = sampleClient.users.me

      behave like clientReceivingUnexpectedResponse(endpoint, request)

      def stub: StubMapping =
        stubFor(
          endpoint
            .willReturn(
              aResponse()
                .withStatus(200)
                .withBodyFile("users/me.json")
            )
        )

      "return the correct entity" in matchResponse(stub, request) {
        case FsResponse(_, _, Right(privateUser)) =>
          privateUser.id shouldBe SpotifyUserId("{f_}")
      }
    }
  }
}
