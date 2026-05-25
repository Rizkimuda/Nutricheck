import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:google_fonts/google_fonts.dart';
import 'providers/nutricheck_provider.dart';
import 'screens/main_navigation.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => NutriCheckProvider()),
      ],
      child: const NutriCheckApp(),
    ),
  );
}

class NutriCheckApp extends StatelessWidget {
  const NutriCheckApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'NutriCheck',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF005D37),
          primary: const Color(0xFF005D37),
          secondary: const Color(0xFF735C00),
          tertiary: const Color(0xFFB4271D),
          background: const Color(0xFFEBF1F6),
        ),
        textTheme: GoogleFonts.interTextTheme(Theme.of(context).textTheme),
      ),
      home: const MainNavigation(),
    );
  }
}
