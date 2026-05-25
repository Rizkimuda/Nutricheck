import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class ProductDetailScreen extends StatelessWidget {
  final Map<String, dynamic> analysisResult;

  const ProductDetailScreen({super.key, required this.analysisResult});

  @override
  Widget build(BuildContext context) {
    final produk = analysisResult['produk'];
    final score = analysisResult['skorKesehatan'] as int;
    final riskStatus = analysisResult['statusRisiko'] ?? 'Aman';
    final recommendation = analysisResult['rekomendasi'] ?? '';
    final warnings = analysisResult['peringatanMedis'] as List<dynamic>? ?? [];
    final additives = produk != null ? produk['komposisiZatAditif'] as List<dynamic>? ?? [] : [];

    Color mainColor = const Color(0xFF10B981);
    Color bgColor = const Color(0xFFECFDF5);
    Color borderColor = const Color(0xFFA7F3D0);
    IconData riskIcon = Icons.check_circle_outline_rounded;

    if ('Berisiko'.equalsIgnoreCase(riskStatus)) {
      mainColor = const Color(0xFFEF4444);
      bgColor = const Color(0xFFFEF2F2);
      borderColor = const Color(0xFFFCA5A5);
      riskIcon = Icons.error_outline_rounded;
    } else if ('Waspada'.equalsIgnoreCase(riskStatus)) {
      mainColor = const Color(0xFFF59E0B);
      bgColor = const Color(0xFFFFFBEB);
      borderColor = const Color(0xFFFCD34D);
      riskIcon = Icons.warning_amber_rounded;
    }

    return Scaffold(
      backgroundColor: const Color(0xFFF8FAFC),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new_rounded, color: Color(0xFF1E293B)),
          onPressed: () => Navigator.pop(context),
        ),
        title: Text(
          'Hasil Analisis',
          style: GoogleFonts.outfit(
            color: const Color(0xFF1E293B),
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(24),
                border: Border.all(color: const Color(0xFFE2E8F0)),
              ),
              child: Row(
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.circular(16),
                    child: Image.network(
                      produk != null ? produk['gambarProduk'] ?? '' : '',
                      width: 80,
                      height: 80,
                      fit: BoxFit.cover,
                      errorBuilder: (_, __, ___) => Container(
                        width: 80,
                        height: 80,
                        color: const Color(0xFFF1F5F9),
                        child: const Icon(Icons.fastfood_rounded, color: Color(0xFF1E3A8A), size: 30),
                      ),
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          produk != null ? produk['namaProduk'] : 'Produk',
                          style: GoogleFonts.outfit(
                            fontWeight: FontWeight.bold,
                            fontSize: 18,
                            color: const Color(0xFF1E293B),
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          produk != null ? produk['kategori'] ?? 'Kategori Umum' : '',
                          style: GoogleFonts.outfit(
                            fontSize: 13,
                            color: Colors.grey.shade500,
                          ),
                        ),
                        const SizedBox(height: 6),
                        Text(
                          'Barcode: ${produk != null ? produk['barcode'] : ''}',
                          style: GoogleFonts.outfit(
                            fontSize: 12,
                            color: Colors.grey.shade400,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                color: bgColor,
                borderRadius: BorderRadius.circular(24),
                border: Border.all(color: borderColor, width: 1),
              ),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Row(
                        children: [
                          Icon(riskIcon, color: mainColor, size: 24),
                          const SizedBox(width: 8),
                          Text(
                            'Status: $riskStatus',
                            style: GoogleFonts.outfit(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                              color: mainColor,
                            ),
                          ),
                        ],
                      ),
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
                        decoration: BoxDecoration(
                          color: mainColor,
                          borderRadius: BorderRadius.circular(16),
                        ),
                        child: Text(
                          'Skor: $score',
                          style: GoogleFonts.outfit(
                            fontSize: 15,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 16),
                  const Divider(color: Colors.black12, height: 1),
                  const SizedBox(height: 16),
                  Text(
                    recommendation,
                    style: GoogleFonts.outfit(
                      fontSize: 14,
                      fontWeight: FontWeight.w500,
                      color: const Color(0xFF1E293B),
                      height: 1.4,
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 24),
            if (warnings.isNotEmpty) ...[
              Text(
                'Peringatan Personalisasi Medis',
                style: GoogleFonts.outfit(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: const Color(0xFF1E293B),
                ),
              ),
              const SizedBox(height: 12),
              ...warnings.map((w) {
                return Container(
                  margin: const EdgeInsets.only(bottom: 10),
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: const Color(0xFFFFF1F2),
                    borderRadius: BorderRadius.circular(16),
                    border: Border.all(color: const Color(0xFFFECDD3), width: 1),
                  ),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Icon(Icons.gpp_bad_rounded, color: Color(0xFFE11D48), size: 22),
                      const SizedBox(width: 12),
                      Expanded(
                        child: Text(
                          w.toString(),
                          style: GoogleFonts.outfit(
                            fontSize: 13,
                            fontWeight: FontWeight.w600,
                            color: const Color(0xFF9F1239),
                            height: 1.4,
                          ),
                        ),
                      ),
                    ],
                  ),
                );
              }).toList(),
              const SizedBox(height: 24),
            ],
            Text(
              'Komposisi Zat Aditif (${additives.length})',
              style: GoogleFonts.outfit(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: const Color(0xFF1E293B),
              ),
            ),
            const SizedBox(height: 12),
            if (additives.isEmpty)
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(16),
                  border: Border.all(color: const Color(0xFFE2E8F0)),
                ),
                child: Text(
                  'Produk ini bersih dari zat aditif terdaftar.',
                  style: GoogleFonts.outfit(
                    fontSize: 13,
                    color: Colors.grey.shade500,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              )
            else
              ...additives.map((zat) {
                final risk = zat['tingkatRisiko'] ?? 'AMAN';
                Color riskColor = const Color(0xFF10B981);
                Color riskBg = const Color(0xFFECFDF5);
                if ('BERBAHAYA'.equalsIgnoreCase(risk)) {
                  riskColor = const Color(0xFFEF4444);
                  riskBg = const Color(0xFFFEF2F2);
                } else if ('WASPADA'.equalsIgnoreCase(risk)) {
                  riskColor = const Color(0xFFF59E0B);
                  riskBg = const Color(0xFFFFFBEB);
                }

                return Container(
                  margin: const EdgeInsets.only(bottom: 12),
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(18),
                    border: Border.all(color: const Color(0xFFE2E8F0)),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Expanded(
                            child: Text(
                              zat['namaUmum'] ?? '',
                              style: GoogleFonts.outfit(
                                fontSize: 15,
                                fontWeight: FontWeight.bold,
                                color: const Color(0xFF1E293B),
                              ),
                            ),
                          ),
                          Container(
                            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                            decoration: BoxDecoration(
                              color: riskBg,
                              borderRadius: BorderRadius.circular(8),
                            ),
                            child: Text(
                              risk,
                              style: GoogleFonts.outfit(
                                fontSize: 11,
                                fontWeight: FontWeight.bold,
                                color: riskColor,
                              ),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 8),
                      Text(
                        'Nama Ilmiah: ${zat['namaIlmiah'] ?? '-'}',
                        style: GoogleFonts.outfit(
                          fontSize: 12,
                          color: Colors.grey.shade500,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Fungsi: ${zat['fungsi'] ?? '-'}',
                        style: GoogleFonts.outfit(
                          fontSize: 12,
                          color: Colors.grey.shade500,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Ambang Batas Harian: ${zat['ambangBatasHarian'] ?? '-'}',
                        style: GoogleFonts.outfit(
                          fontSize: 12,
                          color: Colors.grey.shade500,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                );
              }).toList(),
          ],
        ),
      ),
    );
  }
}

extension on String {
  bool equalsIgnoreCase(String other) {
    return toLowerCase() == other.toLowerCase();
  }
}
