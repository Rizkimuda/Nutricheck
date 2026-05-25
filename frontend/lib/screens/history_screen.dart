import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:intl/intl.dart';
import '../providers/nutricheck_provider.dart';
import 'product_detail_screen.dart';

class HistoryScreen extends StatefulWidget {
  const HistoryScreen({super.key});

  @override
  State<HistoryScreen> createState() => _HistoryScreenState();
}

class _HistoryScreenState extends State<HistoryScreen> {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      final provider = Provider.of<NutriCheckProvider>(context, listen: false);
      if (provider.currentUser != null && provider.currentUser!['idPengguna'] != null) {
        provider.fetchRiwayat(provider.currentUser!['idPengguna']);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<NutriCheckProvider>(context);
    final history = provider.riwayatAnalisis;

    return Scaffold(
      backgroundColor: const Color(0xFFF8FAFC),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          'Riwayat Pemindaian',
          style: GoogleFonts.outfit(
            color: const Color(0xFF1E293B),
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
        ),
        centerTitle: true,
      ),
      body: history.isEmpty
          ? Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.history_toggle_off_rounded,
                    size: 64,
                    color: Colors.grey.shade300,
                  ),
                  const SizedBox(height: 16),
                  Text(
                    'Belum ada produk yang dipindai',
                    style: GoogleFonts.outfit(
                      color: Colors.grey.shade500,
                      fontSize: 14,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ],
              ),
            )
          : ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: history.length,
              itemBuilder: (context, index) {
                final item = history[index];
                final produk = item['produk'];
                final score = item['skorKesehatan'] as int;
                final dateStr = item['waktuPemindaian'] ?? '';

                String formattedDate = '';
                try {
                  if (dateStr.isNotEmpty) {
                    final parsedDate = DateTime.parse(dateStr);
                    formattedDate = DateFormat('dd MMM yyyy, HH:mm').format(parsedDate);
                  }
                } catch (_) {
                  formattedDate = dateStr;
                }

                Color colorIndicator = const Color(0xFF10B981);
                String riskText = 'Aman';
                if (score < 45) {
                  colorIndicator = const Color(0xFFEF4444);
                  riskText = 'Berisiko';
                } else if (score < 75) {
                  colorIndicator = const Color(0xFFF59E0B);
                  riskText = 'Waspada';
                }

                return Container(
                  margin: const EdgeInsets.only(bottom: 12),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(16),
                    border: Border.all(color: const Color(0xFFE2E8F0)),
                  ),
                  child: ListTile(
                    contentPadding: const EdgeInsets.all(12),
                    leading: ClipRRect(
                      borderRadius: BorderRadius.circular(12),
                      child: Image.network(
                        produk != null ? produk['gambarProduk'] ?? '' : '',
                        width: 50,
                        height: 50,
                        fit: BoxFit.cover,
                        errorBuilder: (_, __, ___) => Container(
                          width: 50,
                          height: 50,
                          color: Colors.grey.shade100,
                          child: const Icon(Icons.fastfood_rounded, color: Color(0xFF1E3A8A)),
                        ),
                      ),
                    ),
                    title: Text(
                      produk != null ? produk['namaProduk'] : 'Produk',
                      style: GoogleFonts.outfit(
                        fontWeight: FontWeight.bold,
                        fontSize: 15,
                        color: const Color(0xFF1E293B),
                      ),
                    ),
                    subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const SizedBox(height: 4),
                        Text(
                          formattedDate,
                          style: GoogleFonts.outfit(
                            fontSize: 12,
                            color: Colors.grey.shade500,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                          decoration: BoxDecoration(
                            color: colorIndicator.withOpacity(0.1),
                            borderRadius: BorderRadius.circular(6),
                          ),
                          child: Text(
                            riskText,
                            style: GoogleFonts.outfit(
                              fontSize: 11,
                              fontWeight: FontWeight.bold,
                              color: colorIndicator,
                            ),
                          ),
                        ),
                      ],
                    ),
                    trailing: Container(
                      width: 44,
                      height: 44,
                      decoration: BoxDecoration(
                        color: colorIndicator.withOpacity(0.05),
                        shape: BoxShape.circle,
                        border: Border.all(color: colorIndicator.withOpacity(0.2)),
                      ),
                      child: Center(
                        child: Text(
                          '$score',
                          style: GoogleFonts.outfit(
                            fontWeight: FontWeight.bold,
                            fontSize: 14,
                            color: colorIndicator,
                          ),
                        ),
                      ),
                    ),
                    onTap: () {
                      final details = {
                        'idAnalisis': item['idAnalisis'],
                        'produk': produk,
                        'skorKesehatan': score,
                        'statusRisiko': riskText,
                        'rekomendasi': item['rekomendasi'],
                        'waktuPemindaian': dateStr,
                        'peringatanMedis': <String>[],
                      };
                      
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => ProductDetailScreen(analysisResult: details),
                        ),
                      );
                    },
                  ),
                );
              },
            ),
    );
  }
}
