import 'dart:convert';
import 'dart:io' show Platform;
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class NutriCheckProvider with ChangeNotifier {
  Map<String, dynamic>? _currentUser;
  List<dynamic> _kondisiKesehatan = [];
  List<dynamic> _riwayatAnalisis = [];
  bool _isLoading = false;

  Map<String, dynamic>? get currentUser => _currentUser;
  List<dynamic> get kondisiKesehatan => _kondisiKesehatan;
  List<dynamic> get riwayatAnalisis => _riwayatAnalisis;
  bool get isLoading => _isLoading;

  String get baseUrl {
    if (kIsWeb) return 'http://localhost:8080/api';
    try {
      if (Platform.isAndroid) {
        return 'http://10.0.2.2:8080/api';
      }
    } catch (_) {}
    return 'http://localhost:8080/api';
  }

  Future<void> fetchCurrentUser() async {
    _isLoading = true;
    notifyListeners();
    try {
      final response = await http.get(Uri.parse('$baseUrl/pengguna'));
      if (response.statusCode == 200) {
        _currentUser = json.decode(response.body);
        if (_currentUser != null && _currentUser!['idPengguna'] != null) {
          await fetchRiwayat(_currentUser!['idPengguna']);
        }
      }
    } catch (_) {}
    _isLoading = false;
    notifyListeners();
  }

  Future<void> fetchKondisiKesehatan() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/kondisi-kesehatan'));
      if (response.statusCode == 200) {
        _kondisiKesehatan = json.decode(response.body);
      }
    } catch (_) {}
    notifyListeners();
  }

  Future<bool> updateUserProfile({
    required String nama,
    required String email,
    required int usia,
    required String jenisKelamin,
    required double beratBadan,
    required double tinggiBadan,
    required List<String> kondisiIds,
  }) async {
    _isLoading = true;
    notifyListeners();
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/pengguna'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'nama': nama,
          'email': email,
          'usia': usia,
          'jenisKelamin': jenisKelamin,
          'beratBadan': beratBadan,
          'tinggiBadan': tinggiBadan,
          'kondisiKesehatanIds': kondisiIds,
        }),
      );
      if (response.statusCode == 200) {
        _currentUser = json.decode(response.body);
        if (_currentUser != null && _currentUser!['idPengguna'] != null) {
          await fetchRiwayat(_currentUser!['idPengguna']);
        }
        _isLoading = false;
        notifyListeners();
        return true;
      }
    } catch (_) {}
    _isLoading = false;
    notifyListeners();
    return false;
  }

  Future<List<dynamic>> searchProducts(String query) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/produk/search?q=$query'));
      if (response.statusCode == 200) {
        return json.decode(response.body);
      }
    } catch (_) {}
    return [];
  }

  Future<Map<String, dynamic>?> scanProductBarcode(String barcode) async {
    _isLoading = true;
    notifyListeners();
    try {
      String url = '$baseUrl/produk/barcode/$barcode';
      if (_currentUser != null && _currentUser!['idPengguna'] != null) {
        url += '?userId=${_currentUser!['idPengguna']}';
      }
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        final result = json.decode(response.body);
        if (_currentUser != null && _currentUser!['idPengguna'] != null) {
          await fetchRiwayat(_currentUser!['idPengguna']);
        }
        _isLoading = false;
        notifyListeners();
        return result;
      }
    } catch (_) {}
    _isLoading = false;
    notifyListeners();
    return null;
  }

  Future<void> fetchRiwayat(String userId) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/riwayat/$userId'));
      if (response.statusCode == 200) {
        _riwayatAnalisis = json.decode(response.body);
      }
    } catch (_) {}
    notifyListeners();
  }
}
