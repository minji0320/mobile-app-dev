package com.example.ch6

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ch6.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(),
    GoogleApiClient.ConnectionCallbacks, // provider 이용 가능 상황, 이용 불가능 상황 시 callback
    GoogleApiClient.OnConnectionFailedListener, // 현재 가용할 provider가 없을 때 callback
    OnMapReadyCallback // 지도 객체 이용 가능한 상황
{

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var providerClient: FusedLocationProviderClient // 위치 정보 획득 시 이용

    private lateinit var apiClient: GoogleApiClient // provider 결정 역할

    private var map: GoogleMap? = null  // 지도 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 퍼미션 체크하여 미허가 시, 퍼미션 요청 다이얼로그 호출 & 결과 판단
        // 이런 모든 코드를 통일 시키기 위해 ActivityResultLauncher를 만들고 권장하고 있음
        // 권한 1개 : ActivityResultContracts.RequestPermission
        // 권한 여러개 : ActivityResultContracts.RequestMultiplePermissions
        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.all { permission -> permission.value == true }) {
                    apiClient.connect()
                } else {
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show()
                }
            }

        // map 객체 획득 callback 등록
        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?)!!
            .getMapAsync(this)

        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)   // play-service에 다양한 구글 서비스를 위한 api 존재, 그 중 어떤 api?
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        // 위치 정보 획득을 위해서, provider 결정해라 명령
        // 퍼미션 필요, 퍼미션 부여된 것인지에 대한 판단 필요
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            // 퍼미션 허가 다이얼로그 띄우기
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            )
        } else {
            apiClient.connect()
        }
    }

    // 위치 값을 획득한 후에 호출 - 지도 제어
    private fun moveMap(latitude: Double, longitude: Double) {
        // 지도에서의 특정 위치 지정
        val latLng = LatLng(latitude, longitude)

        // 지도 중앙점 띄우기 정보
        val position = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()

        // 지도 중앙점 이동하기
        map!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 정보 - 개발자 지정 이미지를 지도 위에
        val option = MarkerOptions().apply {
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            position(latLng)
            title("My Location")
        }
        // 마커 올리기
        map?.addMarker(option)

    }

    // apiClient에 의해 provider가 결정되어 사용 가능해졌을 때
    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        // 위치 획득
        providerClient.lastLocation.addOnSuccessListener {
            it?.let {
                // 유저 현 위치 획득
                val latitude = it.latitude
                val longitude = it.longitude
                moveMap(latitude, longitude)
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        // 이용하던 provider가 이용 불가능으로 변경될 때
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        // 현 시점에서 가용한 provider가 없는 경우
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
    }
}