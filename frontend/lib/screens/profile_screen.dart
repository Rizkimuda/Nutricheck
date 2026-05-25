import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:google_fonts/google_fonts.dart';
import '../providers/nutricheck_provider.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _namaController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _usiaController = TextEditingController();
  final TextEditingController _beratController = TextEditingController();
  final TextEditingController _tinggiController = TextEditingController();
  String _jenisKelamin = 'Laki-laki';
  List<String> _selectedKondisiIds = [];

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) async {
      final provider = Provider.of<NutriCheckProvider>(context, listen: false);
      await provider.fetchCurrentUser();
      await provider.fetchKondisiKesehatan();
      _populateUserData();
    });
  }

  void _populateUserData() {
    final provider = Provider.of<NutriCheckProvider>(context, listen: false);
    final user = provider.currentUser;
    if (user != null) {
      setState(() {
        _namaController.text = user['nama'] ?? '';
        _emailController.text = user['email'] ?? '';
        _usiaController.text = (user['usia'] ?? '').toString();
        _beratController.text = (user['beratBadan'] ?? '').toString();
        _tinggiController.text = (user['tinggiBadan'] ?? '').toString();
        _jenisKelamin = user['jenisKelamin'] ?? 'Laki-laki';

        if (user['kondisiKesehatan'] != null) {
          _selectedKondisiIds = (user['kondisiKesehatan'] as List)
              .map((k) => k['idKondisi'].toString())
              .toList();
        }
      });
    }
  }

  @override
  void dispose() {
    _namaController.dispose();
    _emailController.dispose();
    _usiaController.dispose();
    _beratController.dispose();
    _tinggiController.dispose();
    super.dispose();
  }

  void _saveProfile() async {
    if (!_formKey.currentState!.validate()) return;

    final provider = Provider.of<NutriCheckProvider>(context, listen: false);
    final success = await provider.updateUserProfile(
      nama: _namaController.text.trim(),
      email: _emailController.text.trim(),
      usia: int.parse(_usiaController.text.trim()),
      jenisKelamin: _jenisKelamin,
      beratBadan: double.parse(_beratController.text.trim()),
      tinggiBadan: double.parse(_tinggiController.text.trim()),
      kondisiIds: _selectedKondisiIds,
    );

    if (!mounted) return;

    if (success) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Profil kesehatan berhasil diperbarui'),
          backgroundColor: Color(0xFF10B981),
        ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Gagal memperbarui profil kesehatan'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<NutriCheckProvider>(context);

    return Scaffold(
      backgroundColor: const Color(0xFFF8FAFC),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          'Profil Kesehatan',
          style: GoogleFonts.outfit(
            color: const Color(0xFF1E293B),
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
        ),
        centerTitle: true,
      ),
      body: provider.isLoading && provider.currentUser == null
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(20.0),
              child: Form(
                key: _formKey,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      padding: const EdgeInsets.all(20),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(24),
                        border: Border.all(color: const Color(0xFFE2E8F0)),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Informasi Pribadi',
                            style: GoogleFonts.outfit(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                              color: const Color(0xFF1E293B),
                            ),
                          ),
                          const SizedBox(height: 16),
                          _buildTextField(
                            controller: _namaController,
                            label: 'Nama Lengkap',
                            hint: 'Masukkan nama lengkap',
                            icon: Icons.person_outline_rounded,
                            validator: (v) => v!.isEmpty ? 'Nama tidak boleh kosong' : null,
                          ),
                          const SizedBox(height: 14),
                          _buildTextField(
                            controller: _emailController,
                            label: 'Alamat Email',
                            hint: 'Masukkan alamat email',
                            icon: Icons.email_outlined,
                            validator: (v) => v!.isEmpty ? 'Email tidak boleh kosong' : null,
                          ),
                          const SizedBox(height: 14),
                          Row(
                            children: [
                              Expanded(
                                child: _buildTextField(
                                  controller: _usiaController,
                                  label: 'Usia',
                                  hint: 'Usia',
                                  icon: Icons.calendar_today_outlined,
                                  isNumber: true,
                                  validator: (v) => v!.isEmpty ? 'Isi usia' : null,
                                ),
                              ),
                              const SizedBox(width: 12),
                              Expanded(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      'Jenis Kelamin',
                                      style: GoogleFonts.outfit(
                                        fontSize: 13,
                                        fontWeight: FontWeight.w600,
                                        color: const Color(0xFF475569),
                                      ),
                                    ),
                                    const SizedBox(height: 6),
                                    Container(
                                      padding: const EdgeInsets.symmetric(horizontal: 12),
                                      decoration: BoxDecoration(
                                        color: const Color(0xFFF1F5F9),
                                        borderRadius: BorderRadius.circular(14),
                                      ),
                                      child: DropdownButtonHideUnderline(
                                        child: DropdownButton<String>(
                                          value: _jenisKelamin,
                                          isExpanded: true,
                                          items: ['Laki-laki', 'Perempuan'].map((String value) {
                                            return DropdownMenuItem<String>(
                                              value: value,
                                              child: Text(value, style: GoogleFonts.outfit(fontSize: 14)),
                                            );
                                          }).toList(),
                                          onChanged: (val) {
                                            if (val != null) {
                                              setState(() {
                                                _jenisKelamin = val;
                                              });
                                            }
                                          },
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                          const SizedBox(height: 14),
                          Row(
                            children: [
                              Expanded(
                                child: _buildTextField(
                                  controller: _beratController,
                                  label: 'Berat Badan (kg)',
                                  hint: 'Berat',
                                  icon: Icons.monitor_weight_outlined,
                                  isNumber: true,
                                  validator: (v) => v!.isEmpty ? 'Isi berat' : null,
                                ),
                              ),
                              const SizedBox(width: 12),
                              Expanded(
                                child: _buildTextField(
                                  controller: _tinggiController,
                                  label: 'Tinggi Badan (cm)',
                                  hint: 'Tinggi',
                                  icon: Icons.height_rounded,
                                  isNumber: true,
                                  validator: (v) => v!.isEmpty ? 'Isi tinggi' : null,
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                    Container(
                      padding: const EdgeInsets.all(20),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(24),
                        border: Border.all(color: const Color(0xFFE2E8F0)),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Kondisi Medis & Alergi',
                            style: GoogleFonts.outfit(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                              color: const Color(0xFF1E293B),
                            ),
                          ),
                          const SizedBox(height: 8),
                          Text(
                            'Pilih kondisi kesehatan atau alergi yang Anda miliki untuk menyesuaikan analisis zat aditif.',
                            style: GoogleFonts.outfit(
                              fontSize: 12,
                              color: Colors.grey.shade500,
                            ),
                          ),
                          const SizedBox(height: 16),
                          provider.kondisiKesehatan.isEmpty
                              ? const Center(child: CircularProgressIndicator())
                              : Column(
                                  children: provider.kondisiKesehatan.map((kondisi) {
                                    final id = kondisi['idKondisi'].toString();
                                    final isChecked = _selectedKondisiIds.contains(id);
                                    return CheckboxListTile(
                                      contentPadding: EdgeInsets.zero,
                                      title: Text(
                                        kondisi['namaKondisi'],
                                        style: GoogleFonts.outfit(
                                          fontSize: 14,
                                          fontWeight: FontWeight.w600,
                                          color: const Color(0xFF1E293B),
                                        ),
                                      ),
                                      subtitle: Text(
                                        kondisi['jenis'],
                                        style: GoogleFonts.outfit(
                                          fontSize: 12,
                                          color: Colors.grey.shade500,
                                        ),
                                      ),
                                      value: isChecked,
                                      activeColor: const Color(0xFF1E3A8A),
                                      checkboxShape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(6),
                                      ),
                                      onChanged: (bool? value) {
                                        setState(() {
                                          if (value == true) {
                                            _selectedKondisiIds.add(id);
                                          } else {
                                            _selectedKondisiIds.remove(id);
                                          }
                                        });
                                      },
                                    );
                                  }).toList(),
                                ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 30),
                    SizedBox(
                      width: double.infinity,
                      height: 54,
                      child: ElevatedButton(
                        onPressed: provider.isLoading ? null : _saveProfile,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF1E3A8A),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(16),
                          ),
                          elevation: 0,
                        ),
                        child: provider.isLoading
                            ? const CircularProgressIndicator(color: Colors.white)
                            : Text(
                                'Simpan Perubahan',
                                style: GoogleFonts.outfit(
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.white,
                                ),
                              ),
                      ),
                    ),
                    const SizedBox(height: 30),
                  ],
                ),
              ),
            ),
    );
  }

  Widget _buildTextField({
    required TextEditingController controller,
    required String label,
    required String hint,
    required IconData icon,
    bool isNumber = false,
    String? Function(String?)? validator,
  }) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: GoogleFonts.outfit(
            fontSize: 13,
            fontWeight: FontWeight.w600,
            color: const Color(0xFF475569),
          ),
        ),
        const SizedBox(height: 6),
        Container(
          decoration: BoxDecoration(
            color: const Color(0xFFF1F5F9),
            borderRadius: BorderRadius.circular(14),
          ),
          child: TextFormField(
            controller: controller,
            keyboardType: isNumber ? TextInputType.number : TextInputType.text,
            validator: validator,
            style: GoogleFonts.outfit(fontSize: 14, color: const Color(0xFF1E293B)),
            decoration: InputDecoration(
              hintText: hint,
              hintStyle: GoogleFonts.outfit(color: Colors.grey.shade400, fontSize: 13),
              prefixIcon: Icon(icon, color: const Color(0xFF64748B), size: 20),
              border: InputBorder.none,
              contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
            ),
          ),
        ),
      ],
    );
  }
}
