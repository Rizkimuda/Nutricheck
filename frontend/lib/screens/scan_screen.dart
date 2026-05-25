import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import '../providers/nutricheck_provider.dart';
import 'product_detail_screen.dart';

class ScanScreen extends StatefulWidget {
  const ScanScreen({super.key});

  @override
  State<ScanScreen> createState() => _ScanScreenState();
}

class _ScanScreenState extends State<ScanScreen> {
  final TextEditingController _barcodeController = TextEditingController();
  bool _isProcessing = false;
  late final MobileScannerController cameraController;
  bool _isCameraMirrored = false;

  @override
  void initState() {
    super.initState();
    cameraController = MobileScannerController(
      detectionSpeed: DetectionSpeed.normal,
      detectionTimeoutMs: 150, // Deteksi super cepat setiap 150ms
      cameraResolution: const Size(1280, 720), // Paksa resolusi HD 720p agar garis barcode tipis terbaca tajam
      formats: const [
        BarcodeFormat.ean8,
        BarcodeFormat.ean13,
        BarcodeFormat.upcA,
        BarcodeFormat.upcE,
        BarcodeFormat.code128,
        BarcodeFormat.qrCode,
      ],
    );
  }

  @override
  void dispose() {
    cameraController.dispose();
    _barcodeController.dispose();
    super.dispose();
  }

  void _handleBarcode(String barcode) async {
    if (_isProcessing) return;
    setState(() {
      _isProcessing = true;
    });

    final provider = Provider.of<NutriCheckProvider>(context, listen: false);
    final result = await provider.scanProductBarcode(barcode);

    setState(() {
      _isProcessing = false;
    });

    if (!mounted) return;

    if (result != null) {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => ProductDetailScreen(analysisResult: result),
        ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Barcode tidak terdaftar'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F172A),
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        title: Text(
          'Pindai Barcode',
          style: GoogleFonts.outfit(
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(height: 20),
              Text(
                'Arahkan kamera ke barcode produk atau masukkan barcode secara manual di bawah.',
                textAlign: TextAlign.center,
                style: GoogleFonts.outfit(
                  color: Colors.grey.shade400,
                  fontSize: 14,
                ),
              ),
              const SizedBox(height: 30),
              Container(
                height: 280,
                width: double.infinity,
                decoration: BoxDecoration(
                  color: Colors.black,
                  borderRadius: BorderRadius.circular(24),
                  border: Border.all(color: const Color(0xFF3B82F6), width: 2),
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(22),
                  child: Stack(
                    alignment: Alignment.center,
                    children: [
                      Transform.scale(
                        scaleX: _isCameraMirrored ? -1.0 : 1.0,
                        child: MobileScanner(
                          controller: cameraController,
                          onDetect: (capture) {
                            final List<Barcode> barcodes = capture.barcodes;
                            for (final barcode in barcodes) {
                              if (barcode.rawValue != null) {
                                _handleBarcode(barcode.rawValue!);
                                break;
                              }
                            }
                          },
                        ),
                      ),
                      Container(
                        width: 220,
                        height: 120,
                        decoration: BoxDecoration(
                          border: Border.all(color: const Color(0xFF10B981), width: 3),
                          borderRadius: BorderRadius.circular(16),
                        ),
                      ),
                      Positioned(
                        top: 12,
                        right: 12,
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(12),
                          child: Container(
                            color: Colors.black54,
                            child: IconButton(
                              icon: Icon(
                                _isCameraMirrored ? Icons.flip_camera_ios_rounded : Icons.flip_camera_ios_outlined,
                                color: const Color(0xFF10B981),
                                size: 20,
                              ),
                              onPressed: () {
                                setState(() {
                                  _isCameraMirrored = !_isCameraMirrored;
                                });
                              },
                              tooltip: 'Cerminkan Kamera',
                            ),
                          ),
                        ),
                      ),
                      if (_isProcessing)
                        Container(
                          color: Colors.black54,
                          child: const Center(
                            child: CircularProgressIndicator(color: Color(0xFF10B981)),
                          ),
                        ),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 30),
              Container(
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color: const Color(0xFF1E293B),
                  borderRadius: BorderRadius.circular(24),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Simulasi Input Manual',
                      style: GoogleFonts.outfit(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    ),
                    const SizedBox(height: 6),
                    Text(
                      'Masukkan barcode untuk menguji sistem analisis personalisasi.',
                      style: GoogleFonts.outfit(
                        color: Colors.grey.shade400,
                        fontSize: 12,
                      ),
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: Container(
                            decoration: BoxDecoration(
                              color: const Color(0xFF0F172A),
                              borderRadius: BorderRadius.circular(14),
                            ),
                            child: TextField(
                              controller: _barcodeController,
                              style: const TextStyle(color: Colors.white),
                              keyboardType: TextInputType.number,
                              decoration: InputDecoration(
                                hintText: 'Contoh: 089686386219',
                                hintStyle: GoogleFonts.outfit(color: Colors.grey.shade600),
                                border: InputBorder.none,
                                contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                              ),
                            ),
                          ),
                        ),
                        const SizedBox(width: 10),
                        ElevatedButton(
                          onPressed: () {
                            if (_barcodeController.text.trim().isNotEmpty) {
                              _handleBarcode(_barcodeController.text.trim());
                            }
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: const Color(0xFF3B82F6),
                            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 14),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(14),
                            ),
                          ),
                          child: Text(
                            'Pindai',
                            style: GoogleFonts.outfit(
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Text(
                      'Barcode Demo yang Tersedia:',
                      style: GoogleFonts.outfit(
                        color: Colors.white70,
                        fontSize: 12,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                    const SizedBox(height: 10),
                    Row(
                      children: [
                        Expanded(
                          child: InkWell(
                            onTap: () {
                              _barcodeController.text = '089686386219';
                              _handleBarcode('089686386219');
                            },
                            child: Container(
                              padding: const EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                color: const Color(0xFF0F172A),
                                borderRadius: BorderRadius.circular(8),
                                border: Border.all(color: Colors.grey.shade800),
                              ),
                              child: Text(
                                'Bumbu Racik\n089686386219',
                                textAlign: TextAlign.center,
                                style: GoogleFonts.outfit(color: Colors.grey.shade400, fontSize: 10),
                              ),
                            ),
                          ),
                        ),
                        const SizedBox(width: 6),
                        Expanded(
                          child: InkWell(
                            onTap: () {
                              _barcodeController.text = '089686017755';
                              _handleBarcode('089686017755');
                            },
                            child: Container(
                              padding: const EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                color: const Color(0xFF0F172A),
                                borderRadius: BorderRadius.circular(8),
                                border: Border.all(color: Colors.grey.shade800),
                              ),
                              child: Text(
                                'Sarimi\n089686017755',
                                textAlign: TextAlign.center,
                                style: GoogleFonts.outfit(color: Colors.grey.shade400, fontSize: 10),
                              ),
                            ),
                          ),
                        ),
                        const SizedBox(width: 6),
                        Expanded(
                          child: InkWell(
                            onTap: () {
                              _barcodeController.text = '089686010824';
                              _handleBarcode('089686010824');
                            },
                            child: Container(
                              padding: const EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                color: const Color(0xFF0F172A),
                                borderRadius: BorderRadius.circular(8),
                                border: Border.all(color: Colors.grey.shade800),
                              ),
                              child: Text(
                                'Indomie\n089686010824',
                                textAlign: TextAlign.center,
                                style: GoogleFonts.outfit(color: Colors.grey.shade400, fontSize: 10),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 40),
            ],
          ),
        ),
      ),
    );
  }
}