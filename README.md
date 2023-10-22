# 📌[Rest API](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)

## 🪶카카오 로그인 과정 - 시퀀스 다이어그램
![](/document\kakaologin_sequence.png)

## 🪶절차
1. 인가 코드 받기
1. 토큰 받기
1. 사용자 로그인 처리

# 📌[인가 코드 받기](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code)

## 🪶사전 설정
- 필수 사항
   - 플랫폼 등록
   - 카카오 로그인 활성화
   - Redirect URI 등록
   - 동의 항목
- 선택 사항
   - OpenID Connect 활성화
   - 간편가입

카카오 로그인 동의 화면을 호출하고, 사용자 동의를 거쳐 인가 코드를 발급합니다. 인가 코드를 사용해 토큰 받기를 요청할 수 있습니다.

## 📨인가 코드 요청 : GET
```
https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}
```

### 필수 쿼리 파리미터
이름|설명|확인 위치
--|--|--
`client_id`|앱 REST API 키|[내 애플리케이션] > [앱 키]에서 확인 가능
`redirect_uri`|인가 코드를 전달받을 서비스 서버의 URI,또한 URL Encoder 절차 필수|[내 애플리케이션] > [카카오 로그인] > [Redirect URI]에서 등록
`response_type`|`code`로 고정

사용자는 동의 화면에서 서비스 이용에 필요한 동의 항목에 동의하고 *로그인하거나 로그인을 취소할 수 있습니다.*  
요청 처리 결과를 담은 쿼리 스트링(Query string)을 redirect_uri로 HTTP 302 리다이렉트(Redirect)합니다.
> Redirect URI는 [내 애플리케이션] > [카카오 로그인] > [Redirect URI]에 등록된 값 중 하나여야 합니다.

### 응답: 사용자가 [동의하고 계속하기] 선택, 로그인 진행
```
HTTP/1.1 302 Found
Content-Length: 0
Location: ${REDIRECT_URI}?code=${AUTHORIZE_CODE}
```
인가 코드 받기 요청 성공
- code 및 state가 전달된 경우
- code의 인가 코드 값으로 토큰 받기 요청

### 응답: 로그인 취소 시
```
HTTP/1.1 302 Found
Content-Length: 0
Location: ${REDIRECT_URI}?error=access_denied&error_description=User%20denied%20access
```
인가 코드 받기 요청 실패
- error 및 error_description이 전달된 경우
- 문제 해결, 응답 코드를 참고해 에러 원인별 상황에 맞는 서비스 페이지나 안내 문구를 사용자에게 보여주도록 처리

# 📌[토큰 받기](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token)
*인가 코드 받기*만으로는 카카오 로그인이 완료되지 않으며, 토큰 받기까지 마쳐야 카카오 로그인을 정상적으로 완료할 수 있습니다.

*필수 파라미터를 포함해 POST로 요청*합니다. 요청 성공 시 *응답은 토큰과 토큰 정보를 포함합니다.*

액세스 토큰으로 사용자 정보 가져오기와 같은 카카오 API를 호출할 수 있습니다. *토큰 정보 보기로 액세스 토큰 유효성 검증 후, 사용자 정보 가져오기를 요청해 필요한 사용자 정보를 받아 서비스 회원 가입 및 로그인을 완료합니다.*

## 🪶사전 설정
- 필수 사항
   - 플랫폼 등록
   - 카카오 로그인 활성화
   - Redirect URI 등록
   - 동의 항목
- 선택 사항
   - OpenID Connect 활성화

## 📨토큰 요청 : POST
```
https://kauth.kakao.com/oauth/token
```

### 헤더
이름|입력 값
--|--
`Content-type`|`application/x-www-form-urlencoded;charset=utf-8`

### 파라미터
이름|설명|필수
--|--|--
`grant_type`|`authorization_code`로 고정|O
`client_id`|앱 REST API 키|O
`redirect_uri`|인가 코드가 리다이렉트된 URI|O
`code`|인가 코드 받기 요청으로 얻은 인가 코드, `${AUTHORIZE_CODE}`|O
`client_secret`|토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드|X

### 응답: 성공
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
    "token_type":"bearer",
    "access_token":"${ACCESS_TOKEN}",
    "expires_in":43199,
    "refresh_token":"${REFRESH_TOKEN}",
    "refresh_token_expires_in":5184000,
    "scope":"account_email profile"
}
```

# 📌[로그아웃](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#logout)
사용자 액세스 토큰과 리프레시 토큰을 모두 만료시킵니다. 사용자가 서비스에서 로그아웃할 때 이 API를 호출하여 더 이상 해당 사용자의 정보로 카카오 API를 호출할 수 없도록 합니다.

- 액세스 토큰으로 요청
   - 해당 액세스 토큰만 만료 처리
   - 만료된 액세스 토큰을 사용하는 모든 기기에서 로그아웃됨
- 서비스 앱 어드민 키로 요청
   - 해당 사용자의 모든 토큰 만료 처리
   - 모든 기기에서 로그아웃됨

로그아웃 요청 성공 시, 응답 코드와 로그아웃된 사용자의 회원번호를 받습니다. 로그아웃 시에도 웹 브라우저의 카카오계정 세션은 만료되지 않고, 로그아웃을 호출한 앱의 토큰만 만료됩니다. 따라서 웹 브라우저의 카카오계정 로그인 상태는 로그아웃을 호출해도 유지됩니다. 로그아웃 후에는 서비스 초기 화면으로 리다이렉트하는 등 후속 조치를 취하도록 합니다.

## 📨액세스 토큰 방식으로 요청 : POST
```
https://kapi.kakao.com/v1/user/logout
```

### 헤더
이름|입력 값|설명
--|--|--
`Content-Type`|`application/x-www-form-urlencoded`
`Authorization`|`Bearer ${ACCESS_TOKEN}`|사용자 인증 수단, 토큰 받기에서 받은 access_token의 값

### 응답:성공
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
    "id":123456789
}
```

# 📌[사용자 정보 가져오기](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info)

## 📨액세스 토큰 방식으로 요청 : GET/POST
```
https://kapi.kakao.com/v2/user/me
```

### 헤더
이름|입력 값|설명
--|--|--
`Content-Type`|`application/x-www-form-urlencoded`
`Authorization`|`Bearer ${ACCESS_TOKEN}`|사용자 인증 수단, 토큰 받기에서 받은 access_token의 값

### 응답
```
{
   "id": 3110097778,
   "connected_at": "2023-10-21T15:10:56Z",
   "properties":{
   "nickname": "이름",
   "profile_image": "http://img_640x640.jpg",
   "thumbnail_image": "http://k.kakaocdn.net/img_110x110.jpg"
},
   "kakao_account":{
      "profile_nickname_needs_agreement": false,
      "profile_image_needs_agreement": false,
      "profile":{"nickname": "이름", "thumbnail_image_url": "jpg",…}
   }
}
```