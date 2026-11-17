"""
SIMATS Java Programming Assignment 1 - Publication-Quality UML Diagrams
Author: Bala Naga Jaswanth (Reg: 192424300)
Design Standard: Enterprise Figma Design System (UML 2.5 Standard, High Legibility at A4)
"""

import os
import pymupdf

OUTPUT_DIR = r"D:\Data\Development\College\Java\Assessment-1\diagrams"
os.makedirs(OUTPUT_DIR, exist_ok=True)

# Shared SVG Defs and CSS Styles optimized for A4 print and screen readability
SVG_COMMON_DEFS = """
  <defs>
    <style>
      .canvas-title { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 22px; font-weight: 700; fill: #0F172A; }
      .canvas-subtitle { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 13.5px; font-weight: 400; fill: #475569; }
      .cls-header-text { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 16px; font-weight: 700; fill: #FFFFFF; }
      .cls-meta-text { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 12px; font-style: italic; fill: #E2E8F0; }
      .code-member { font-family: 'Consolas', 'Courier New', monospace; font-size: 13.5px; fill: #0F172A; }
      .code-comment { font-family: 'Segoe UI', Inter, sans-serif; font-size: 11.5px; font-style: italic; fill: #0284C7; }
      .card-title { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 13px; font-weight: 700; fill: #92400E; }
      .card-body { font-family: 'Segoe UI', Inter, -apple-system, sans-serif; font-size: 12.5px; fill: #78350F; line-height: 1.5; }
      .mult-label { font-family: 'Consolas', monospace; font-size: 13px; font-weight: 700; fill: #1E3A8A; }
      .conn-text { font-family: 'Segoe UI', Inter, sans-serif; font-size: 12px; font-weight: 600; fill: #334155; }
      .seq-head { font-family: 'Segoe UI', Inter, sans-serif; font-size: 14.5px; font-weight: 700; fill: #FFFFFF; }
      .seq-call-text { font-family: 'Consolas', monospace; font-size: 12.5px; font-weight: 600; fill: #0F172A; }
      .seq-ret-text { font-family: 'Consolas', monospace; font-size: 12.5px; font-weight: 600; fill: #2563EB; }
    </style>
    <filter id="soft-shadow" x="-4%" y="-4%" width="108%" height="112%" filterUnits="objectBoundingBox">
      <feDropShadow dx="0" dy="3" stdDeviation="4" flood-color="#0F172A" flood-opacity="0.06"/>
    </filter>
    <!-- Generalization Arrow (Closed triangle, white fill, navy stroke) -->
    <marker id="generalization" viewBox="0 0 20 20" refX="20" refY="10" markerWidth="14" markerHeight="14" orient="auto-start-reverse">
      <polygon points="0,0 20,10 0,20" fill="#FFFFFF" stroke="#1E3A8A" stroke-width="2" stroke-linejoin="round"/>
    </marker>
    <!-- Realization Arrow (Closed triangle, white fill, indigo stroke) -->
    <marker id="realization" viewBox="0 0 20 20" refX="20" refY="10" markerWidth="14" markerHeight="14" orient="auto-start-reverse">
      <polygon points="0,0 20,10 0,20" fill="#FFFFFF" stroke="#4338CA" stroke-width="2" stroke-linejoin="round"/>
    </marker>
    <!-- Directed Association Arrow -->
    <marker id="assoc-arrow" viewBox="0 0 16 16" refX="16" refY="8" markerWidth="10" markerHeight="10" orient="auto-start-reverse">
      <polyline points="2,2 14,8 2,14" fill="none" stroke="#1E293B" stroke-width="2" stroke-linecap="round"/>
    </marker>
    <!-- Dependency Open Arrow -->
    <marker id="dep-arrow" viewBox="0 0 16 16" refX="16" refY="8" markerWidth="10" markerHeight="10" orient="auto-start-reverse">
      <polyline points="2,2 14,8 2,14" fill="none" stroke="#2563EB" stroke-width="2" stroke-linecap="round"/>
    </marker>
    <!-- Aggregation Diamond (Open diamond, white fill) -->
    <marker id="aggregation" viewBox="0 0 24 16" refX="24" refY="8" markerWidth="16" markerHeight="12" orient="auto-start-reverse">
      <polygon points="0,8 12,0 24,8 12,16" fill="#FFFFFF" stroke="#1E3A8A" stroke-width="2"/>
    </marker>
    <!-- Sequence Synchronous Call (Solid arrow) -->
    <marker id="seq-call" viewBox="0 0 16 16" refX="16" refY="8" markerWidth="9" markerHeight="9" orient="auto-start-reverse">
      <polygon points="0,2 14,8 0,14" fill="#0F172A"/>
    </marker>
    <!-- Sequence Return Message (Dashed open arrow) -->
    <marker id="seq-ret" viewBox="0 0 16 16" refX="16" refY="8" markerWidth="9" markerHeight="9" orient="auto-start-reverse">
      <polyline points="0,2 14,8 0,14" fill="none" stroke="#2563EB" stroke-width="2" stroke-linecap="round"/>
    </marker>
  </defs>
"""

# ==============================================================================
# 1. DIAGRAM A: Task 06 - Vehicle Inheritance Hierarchy
# ==============================================================================
def build_diagram_a():
    svg = f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 940 640" width="940" height="640">
{SVG_COMMON_DEFS}
  <!-- Canvas Background -->
  <rect width="100%" height="100%" fill="#F8FAFC" rx="14"/>
  <rect x="1" y="1" width="938" height="638" fill="none" stroke="#E2E8F0" stroke-width="1.5" rx="13"/>

  <!-- Canvas Header -->
  <text x="35" y="42" class="canvas-title">Task 06: Vehicle Inheritance Hierarchy &amp; Constructor Trace</text>
  <text x="35" y="64" class="canvas-subtitle">Publication-Quality UML 2.5 Class Diagram | Generalization Hierarchy &amp; Dynamic Overriding</text>
  <line x1="35" y1="76" x2="905" y2="76" stroke="#E2E8F0" stroke-width="1.5"/>

  <!-- Superclass: Vehicle -->
  <g transform="translate(330, 95)" filter="url(#soft-shadow)">
    <rect width="280" height="135" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 272,0 A 8,8 0 0,1 280,8 L 280,44 L 0,44 Z" fill="#1E3A8A"/>
    <text x="140" y="20" text-anchor="middle" class="cls-meta-text">&lt;&lt;superclass&gt;&gt;</text>
    <text x="140" y="37" text-anchor="middle" class="cls-header-text">Vehicle</text>
    <line x1="0" y1="44" x2="280" y2="44" stroke="#1E3A8A" stroke-width="1"/>
    <!-- Attributes -->
    <text x="16" y="68" class="code-member">~ regNo: String</text>
    <line x1="0" y1="78" x2="280" y2="78" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="16" y="100" class="code-member">+ Vehicle(regNo: String)</text>
    <text x="16" y="122" class="code-member">+ ratePerHour(): double</text>
  </g>

  <!-- Subclass: TwoWheeler -->
  <g transform="translate(40, 290)" filter="url(#soft-shadow)">
    <rect width="270" height="130" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 262,0 A 8,8 0 0,1 270,8 L 270,42 L 0,42 Z" fill="#0F766E"/>
    <text x="135" y="19" text-anchor="middle" class="cls-meta-text">&lt;&lt;subclass&gt;&gt;</text>
    <text x="135" y="36" text-anchor="middle" class="cls-header-text">TwoWheeler</text>
    <line x1="0" y1="42" x2="270" y2="42" stroke="#0F766E" stroke-width="1"/>
    <!-- Methods -->
    <text x="14" y="68" class="code-member">+ TwoWheeler(regNo: String)</text>
    <text x="14" y="94" class="code-member">+ ratePerHour(): double</text>
    <text x="14" y="114" class="code-comment">/* overrides: Rs 10.0/hr */</text>
  </g>

  <!-- Subclass: Car -->
  <g transform="translate(340, 290)" filter="url(#soft-shadow)">
    <rect width="260" height="130" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 252,0 A 8,8 0 0,1 260,8 L 260,42 L 0,42 Z" fill="#1D4ED8"/>
    <text x="130" y="19" text-anchor="middle" class="cls-meta-text">&lt;&lt;subclass&gt;&gt;</text>
    <text x="130" y="36" text-anchor="middle" class="cls-header-text">Car</text>
    <line x1="0" y1="42" x2="260" y2="42" stroke="#1D4ED8" stroke-width="1"/>
    <!-- Methods -->
    <text x="14" y="68" class="code-member">+ Car(regNo: String)</text>
    <text x="14" y="94" class="code-member">+ ratePerHour(): double</text>
    <text x="14" y="114" class="code-comment">/* overrides: Rs 30.0/hr */</text>
  </g>

  <!-- Sub-subclass: ElectricCar -->
  <g transform="translate(340, 480)" filter="url(#soft-shadow)">
    <rect width="260" height="135" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 252,0 A 8,8 0 0,1 260,8 L 260,42 L 0,42 Z" fill="#4338CA"/>
    <text x="130" y="19" text-anchor="middle" class="cls-meta-text">&lt;&lt;subclass of Car&gt;&gt;</text>
    <text x="130" y="36" text-anchor="middle" class="cls-header-text">ElectricCar</text>
    <line x1="0" y1="42" x2="260" y2="42" stroke="#4338CA" stroke-width="1"/>
    <!-- Attributes -->
    <text x="14" y="66" class="code-member">~ batteryCapacity: int</text>
    <line x1="0" y1="76" x2="260" y2="76" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="14" y="98" class="code-member">+ ElectricCar(regNo, batt)</text>
    <text x="14" y="118" class="code-comment">/* inherits Car rate: Rs 30.0 */</text>
  </g>

  <!-- Connectors (Clean Orthogonal, No Crossing) -->
  <!-- TwoWheeler to Vehicle -->
  <path d="M 175,290 L 175,255 L 430,255 L 430,230" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- Car to Vehicle -->
  <path d="M 470,290 L 470,230" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- ElectricCar to Car -->
  <path d="M 470,480 L 470,420" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- Architectural Callout Panel -->
  <g transform="translate(635, 110)" filter="url(#soft-shadow)">
    <rect width="270" height="295" rx="8" fill="#FFFDF5" stroke="#FDE68A" stroke-width="1.5"/>
    <rect width="6" height="295" rx="3" fill="#F59E0B"/>
    
    <text x="20" y="30" class="card-title">CONSTRUCTOR ORDER &amp; RATES</text>
    <line x1="20" y1="38" x2="255" y2="38" stroke="#FEF3C7" stroke-width="1.5"/>
    
    <text x="20" y="62" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#78350F">1. super() Chaining Trace:</text>
    <text x="26" y="84" class="card-body">&#x2022; Instantiation: ElectricCar("TN03..", 40)</text>
    <text x="26" y="104" class="card-body">&#x2022; ElectricCar calls super(regNo)</text>
    <text x="26" y="124" class="card-body">&#x2022; Car calls super(regNo)</text>
    <text x="26" y="144" class="card-body">&#x2022; Console Output: 1 &#x2192; 2 &#x2192; 3</text>

    <text x="20" y="174" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#78350F">2. Polymorphic Overriding:</text>
    <text x="26" y="196" class="card-body">&#x2022; Vehicle base rate: Rs 20.0</text>
    <text x="26" y="216" class="card-body">&#x2022; TwoWheeler rate: Rs 10.0</text>
    <text x="26" y="236" class="card-body">&#x2022; Car rate: Rs 30.0</text>
    <text x="26" y="256" class="card-body">&#x2022; ElectricCar: inherits Rs 30.0</text>
    <text x="20" y="282" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#047857">&#x2714; Verified against Task06 output</text>
  </g>

  <!-- UML Key Panel -->
  <g transform="translate(635, 430)">
    <rect width="270" height="150" rx="8" fill="#FFFFFF" stroke="#E2E8F0" stroke-width="1.2"/>
    <text x="18" y="26" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#334155">UML 2.5 NOTATION KEY</text>
    <line x1="18" y1="34" x2="252" y2="34" stroke="#F1F5F9" stroke-width="1.2"/>
    
    <line x1="22" y1="58" x2="65" y2="58" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>
    <text x="75" y="62" font-family="'Segoe UI', sans-serif" font-size="12" fill="#475569">Generalization ("is-a")</text>

    <text x="22" y="92" class="code-member">~ package-private</text>
    <text x="145" y="92" class="code-member">+ public</text>
    <text x="22" y="116" class="code-member">- private</text>
    <text x="145" y="116" class="code-member"># protected</text>
    <text x="22" y="138" font-family="'Segoe UI', sans-serif" font-size="11" fill="#64748B" font-style="italic">Source-verified with Task06.java</text>
  </g>
</svg>"""
    save_figma_diagram(svg, "task06_inheritance")

# ==============================================================================
# 2. DIAGRAM B: Task 10 - Package Architecture & Encapsulation
# ==============================================================================
def build_diagram_b():
    svg = f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1020 720" width="1020" height="720">
{SVG_COMMON_DEFS}
  <!-- Canvas Background -->
  <rect width="100%" height="100%" fill="#F8FAFC" rx="14"/>
  <rect x="1" y="1" width="1018" height="718" fill="none" stroke="#E2E8F0" stroke-width="1.5" rx="13"/>

  <!-- Canvas Header -->
  <text x="35" y="42" class="canvas-title">Task 10: Modular Package Architecture &amp; Visibility Encapsulation</text>
  <text x="35" y="64" class="canvas-subtitle">Multi-Package Subsystem Partitioning | Data Hiding, Mutator Validation &amp; Integer Cache</text>
  <line x1="35" y1="76" x2="985" y2="76" stroke="#E2E8F0" stroke-width="1.5"/>

  <!-- ================= PACKAGE 1: parking.app ================= -->
  <g transform="translate(35, 95)">
    <!-- Package Tab -->
    <path d="M 0,0 L 110,0 L 125,22 L 0,22 Z" fill="#E2E8F0"/>
    <text x="10" y="16" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#334155">parking.app</text>
    <!-- Package Boundary -->
    <rect y="22" width="260" height="205" rx="8" fill="#F8FAFC" stroke="#CBD5E1" stroke-width="1.5" stroke-dasharray="5,4"/>
    
    <!-- Class: MainApp -->
    <g transform="translate(15, 40)" filter="url(#soft-shadow)">
      <rect width="230" height="115" rx="6" fill="#FFFFFF" stroke="#94A3B8" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 224,0 A 6,6 0 0,1 230,6 L 230,36 L 0,36 Z" fill="#334155"/>
      <text x="115" y="17" text-anchor="middle" class="cls-meta-text">&lt;&lt;entry point&gt;&gt;</text>
      <text x="115" y="31" text-anchor="middle" class="cls-header-text">MainApp</text>
      <line x1="0" y1="36" x2="230" y2="36" stroke="#334155" stroke-width="1"/>
      <text x="10" y="60" class="code-member">+ main(args: String[]): void</text>
      <line x1="0" y1="70" x2="230" y2="70" stroke="#F1F5F9" stroke-width="1"/>
      <text x="10" y="90" font-family="'Segoe UI', sans-serif" font-size="11" fill="#475569">&#x2022; Tests ParkingService</text>
      <text x="10" y="106" font-family="'Segoe UI', sans-serif" font-size="11" fill="#475569">&#x2022; Tests Integer Cache</text>
    </g>
  </g>

  <!-- ================= PACKAGE 2: parking.service ================= -->
  <g transform="translate(35, 340)">
    <!-- Package Tab -->
    <path d="M 0,0 L 130,0 L 145,22 L 0,22 Z" fill="#DBEAFE"/>
    <text x="10" y="16" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#1E40AF">parking.service</text>
    <!-- Package Boundary -->
    <rect y="22" width="280" height="345" rx="8" fill="#F0F7FF" stroke="#93C5FD" stroke-width="1.5" stroke-dasharray="5,4"/>
    
    <!-- Class: ParkingService -->
    <g transform="translate(15, 40)" filter="url(#soft-shadow)">
      <rect width="250" height="190" rx="6" fill="#FFFFFF" stroke="#93C5FD" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 244,0 A 6,6 0 0,1 250,6 L 250,38 L 0,38 Z" fill="#1E3A8A"/>
      <text x="125" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;service orchestrator&gt;&gt;</text>
      <text x="125" y="32" text-anchor="middle" class="cls-header-text">ParkingService</text>
      <line x1="0" y1="38" x2="250" y2="38" stroke="#1E3A8A" stroke-width="1"/>
      <!-- Attributes -->
      <text x="10" y="60" class="code-member">- facility: ParkingSpot[][]</text>
      <line x1="0" y1="70" x2="250" y2="70" stroke="#F1F5F9" stroke-width="1"/>
      <!-- Methods -->
      <text x="10" y="92" class="code-member">+ ParkingService(f, s)</text>
      <text x="10" y="114" class="code-member">+ allocateSpot(v: Vehicle):</text>
      <text x="24" y="130" class="code-member">  ParkingSpot</text>
      <text x="10" y="152" class="code-member">+ calculateFee(v: Vehicle,</text>
      <text x="24" y="168" class="code-member">  minutes: int): double</text>
    </g>

    <g transform="translate(15, 250)">
      <rect width="250" height="75" rx="4" fill="#FFFFFF" stroke="#BFDBFE" stroke-width="1"/>
      <text x="10" y="20" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#1E3A8A">Safe Ceiling Billing:</text>
      <text x="10" y="42" class="code-member">int h = (minutes + 59)/60;</text>
      <text x="10" y="62" class="code-member">return h * v.ratePerHour();</text>
    </g>
  </g>

  <!-- ================= PACKAGE 3: parking.model ================= -->
  <g transform="translate(355, 95)">
    <!-- Package Tab -->
    <path d="M 0,0 L 130,0 L 145,22 L 0,22 Z" fill="#DCFCE7"/>
    <text x="10" y="16" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#15803D">parking.model</text>
    <!-- Package Boundary -->
    <rect y="22" width="630" height="590" rx="8" fill="#F0FDF4" stroke="#86EFAC" stroke-width="1.5" stroke-dasharray="5,4"/>

    <!-- Abstract Class: Vehicle -->
    <g transform="translate(20, 40)" filter="url(#soft-shadow)">
      <rect width="290" height="195" rx="6" fill="#FFFFFF" stroke="#86EFAC" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 284,0 A 6,6 0 0,1 290,6 L 290,38 L 0,38 Z" fill="#065F46"/>
      <text x="145" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;abstract superclass&gt;&gt;</text>
      <text x="145" y="32" text-anchor="middle" class="cls-header-text">Vehicle</text>
      <line x1="0" y1="38" x2="290" y2="38" stroke="#065F46" stroke-width="1"/>
      <!-- Attributes -->
      <text x="12" y="60" class="code-member">- regNo: String</text>
      <line x1="0" y1="70" x2="290" y2="70" stroke="#F1F5F9" stroke-width="1"/>
      <!-- Methods -->
      <text x="12" y="92" class="code-member">+ Vehicle(regNo: String)</text>
      <text x="12" y="114" class="code-member">+ getRegNo(): String</text>
      <text x="12" y="136" class="code-member">+ setRegNo(regNo): void</text>
      <text x="12" y="158" class="code-member">+ {{abstract}} ratePerHour():</text>
      <text x="26" y="176" class="code-member">  double</text>
    </g>

    <!-- Class: ParkingSpot -->
    <g transform="translate(335, 40)" filter="url(#soft-shadow)">
      <rect width="275" height="240" rx="6" fill="#FFFFFF" stroke="#86EFAC" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 269,0 A 6,6 0 0,1 275,6 L 275,38 L 0,38 Z" fill="#047857"/>
      <text x="137" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;entity state&gt;&gt;</text>
      <text x="137" y="32" text-anchor="middle" class="cls-header-text">ParkingSpot</text>
      <line x1="0" y1="38" x2="275" y2="38" stroke="#047857" stroke-width="1"/>
      <!-- Attributes -->
      <text x="12" y="60" class="code-member">- floor: int</text>
      <text x="12" y="80" class="code-member">- slot: int</text>
      <text x="12" y="100" class="code-member">- vehicle: Vehicle</text>
      <line x1="0" y1="110" x2="275" y2="110" stroke="#F1F5F9" stroke-width="1"/>
      <!-- Methods -->
      <text x="12" y="132" class="code-member">+ ParkingSpot(floor, slot)</text>
      <text x="12" y="152" class="code-member">+ getFloor(): int, getSlot()</text>
      <text x="12" y="172" class="code-member">+ getVehicle(): Vehicle</text>
      <text x="12" y="192" class="code-member">+ isFree(): boolean</text>
      <text x="12" y="212" class="code-member">+ park(v: Vehicle): void</text>
      <text x="12" y="232" class="code-member">+ vacate(): void</text>
    </g>

    <!-- Subclass: Car -->
    <g transform="translate(20, 290)" filter="url(#soft-shadow)">
      <rect width="135" height="95" rx="6" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 129,0 A 6,6 0 0,1 135,6 L 135,30 L 0,30 Z" fill="#1E40AF"/>
      <text x="67" y="21" text-anchor="middle" class="cls-header-text">Car</text>
      <text x="10" y="52" class="code-member">+ Car(regNo)</text>
      <text x="10" y="74" class="code-member">+ ratePerHour()</text>
      <text x="10" y="88" class="code-comment">/* Rs 30.0 */</text>
    </g>

    <!-- Subclass: TwoWheeler -->
    <g transform="translate(170, 290)" filter="url(#soft-shadow)">
      <rect width="140" height="95" rx="6" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
      <path d="M 0,6 A 6,6 0 0,1 6,0 L 134,0 A 6,6 0 0,1 140,6 L 140,30 L 0,30 Z" fill="#0F766E"/>
      <text x="70" y="21" text-anchor="middle" class="cls-header-text">TwoWheeler</text>
      <text x="10" y="52" class="code-member">+ TwoWheeler()</text>
      <text x="10" y="74" class="code-member">+ ratePerHour()</text>
      <text x="10" y="88" class="code-comment">/* Rs 10.0 */</text>
    </g>

    <!-- Encapsulation Defense Card -->
    <g transform="translate(20, 415)">
      <rect width="590" height="155" rx="6" fill="#FFFFFF" stroke="#86EFAC" stroke-width="1.2"/>
      <rect width="5" height="155" rx="2" fill="#10B981"/>
      <text x="16" y="24" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#065F46">ENCAPSULATION &amp; ARCHITECTURAL RULES (Task 10 Defense)</text>
      <line x1="16" y1="32" x2="595" y2="32" stroke="#DCFCE7" stroke-width="1"/>
      
      <text x="16" y="52" font-family="'Segoe UI', sans-serif" font-size="11.5" fill="#1E293B">1. Private Fields &amp; Public Mutators: regNo in Vehicle and floor/slot in ParkingSpot are hidden.</text>
      <text x="16" y="72" font-family="'Segoe UI', sans-serif" font-size="11.5" fill="#1E293B">2. Defensive Invariants: setRegNo() rejects null/blank; calculateFee() rejects negative durations.</text>
      <text x="16" y="92" font-family="'Segoe UI', sans-serif" font-size="11.5" fill="#1E293B">3. Separation of Concerns: MainApp coordinates, ParkingService orchestrates, Spot tracks occupancy.</text>
      <text x="16" y="112" font-family="'Segoe UI', sans-serif" font-size="11.5" fill="#1E293B">4. Integer Cache Invariant: Integer a=100, b=100 (a==b &#x2192; true); Integer c=200, d=200 (c==d &#x2192; false).</text>
      <text x="16" y="132" font-family="'Segoe UI', sans-serif" font-size="11" fill="#059669" font-style="italic">&#x2714; Verified against active Task 10 source code and execution logs.</text>
    </g>

    <!-- Connectors inside model -->
    <!-- Car to Vehicle -->
    <path d="M 87,290 L 87,255 L 110,255 L 110,235" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>
    <!-- TwoWheeler to Vehicle -->
    <path d="M 240,290 L 240,255 L 160,255 L 160,235" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>
    <!-- ParkingSpot to Vehicle -->
    <path d="M 335,110 L 310,110" fill="none" stroke="#1E293B" stroke-width="1.8" marker-end="url(#assoc-arrow)"/>
    <text x="314" y="102" class="mult-label">0..1</text>
  </g>

  <!-- Cross-Package Connectors (No Crossings) -->
  <!-- MainApp to ParkingService -->
  <path d="M 145,245 L 145,380" fill="none" stroke="#2563EB" stroke-width="1.8" stroke-dasharray="5,4" marker-end="url(#dep-arrow)"/>
  <text x="155" y="310" class="conn-text">&lt;&lt;uses&gt;&gt;</text>

  <!-- MainApp to Vehicle -->
  <path d="M 295,150 L 375,150" fill="none" stroke="#2563EB" stroke-width="1.8" stroke-dasharray="5,4" marker-end="url(#dep-arrow)"/>
  <text x="305" y="142" class="conn-text">&lt;&lt;creates&gt;&gt;</text>

  <!-- ParkingService to ParkingSpot -->
  <path d="M 315,440 L 780,440 L 780,335" fill="none" stroke="#1E3A8A" stroke-width="2" marker-start="url(#aggregation)"/>
  <text x="325" y="432" class="mult-label">1</text>
  <text x="758" y="350" class="mult-label">*</text>
  <text x="490" y="432" class="conn-text">manages 2D spots</text>
</svg>"""
    save_figma_diagram(svg, "task10_packages")

# ==============================================================================
# 3. DIAGRAM C: Task 11 - Core System UML Class Diagram
# ==============================================================================
def build_diagram_c():
    svg = f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1060 720" width="1060" height="720">
{SVG_COMMON_DEFS}
  <!-- Canvas Background -->
  <rect width="100%" height="100%" fill="#F8FAFC" rx="14"/>
  <rect x="1" y="1" width="1058" height="718" fill="none" stroke="#E2E8F0" stroke-width="1.5" rx="13"/>

  <!-- Canvas Header -->
  <text x="35" y="42" class="canvas-title">Task 11: Integrated Smart-Parking System Architecture</text>
  <text x="35" y="64" class="canvas-subtitle">Full UML 2.5 Class Diagram | Vehicle Polymorphism, Ticket Tracking &amp; Facility Management</text>
  <line x1="35" y1="76" x2="1025" y2="76" stroke="#E2E8F0" stroke-width="1.5"/>

  <!-- Interface: Chargeable -->
  <g transform="translate(40, 95)" filter="url(#soft-shadow)">
    <rect width="240" height="110" rx="8" fill="#FFFFFF" stroke="#4338CA" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 232,0 A 8,8 0 0,1 240,8 L 240,40 L 0,40 Z" fill="#4338CA"/>
    <text x="120" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;interface&gt;&gt;</text>
    <text x="120" y="34" text-anchor="middle" class="cls-header-text">Chargeable</text>
    <line x1="0" y1="40" x2="240" y2="40" stroke="#4338CA" stroke-width="1"/>
    <text x="12" y="68" class="code-member">+ chargingFee(units: double):</text>
    <text x="26" y="88" class="code-member">  double</text>
  </g>

  <!-- Abstract Class: Vehicle -->
  <g transform="translate(320, 95)" filter="url(#soft-shadow)">
    <rect width="310" height="235" rx="8" fill="#FFFFFF" stroke="#047857" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 302,0 A 8,8 0 0,1 310,8 L 310,42 L 0,42 Z" fill="#047857"/>
    <text x="155" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;abstract superclass&gt;&gt;</text>
    <text x="155" y="34" text-anchor="middle" class="cls-header-text">Vehicle</text>
    <line x1="0" y1="42" x2="310" y2="42" stroke="#047857" stroke-width="1"/>
    <!-- Attributes -->
    <text x="14" y="66" class="code-member">~ regNo: String</text>
    <text x="14" y="88" class="code-member">~ monthlyPass: boolean</text>
    <line x1="0" y1="98" x2="310" y2="98" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="14" y="120" class="code-member">~ Vehicle(regNo, pass)</text>
    <text x="14" y="142" class="code-member">~ {{abstract}} ratePerHour(): double</text>
    <text x="14" y="164" class="code-member">~ parkingFee(minutes: int): double</text>
    <text x="14" y="186" class="code-member">+ equals(obj: Object): boolean</text>
    <text x="14" y="208" class="code-member">+ hashCode(): int</text>
  </g>

  <!-- Subclass: TwoWheeler -->
  <g transform="translate(320, 375)" filter="url(#soft-shadow)">
    <rect width="195" height="105" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 187,0 A 8,8 0 0,1 195,8 L 195,36 L 0,36 Z" fill="#0F766E"/>
    <text x="97" y="24" text-anchor="middle" class="cls-header-text">TwoWheeler</text>
    <line x1="0" y1="36" x2="195" y2="36" stroke="#0F766E" stroke-width="1"/>
    <text x="12" y="60" class="code-member">~ TwoWheeler(reg, pass)</text>
    <text x="12" y="82" class="code-member">~ ratePerHour(): double</text>
  </g>

  <!-- Subclass: Car -->
  <g transform="translate(540, 375)" filter="url(#soft-shadow)">
    <rect width="195" height="105" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 187,0 A 8,8 0 0,1 195,8 L 195,36 L 0,36 Z" fill="#1D4ED8"/>
    <text x="97" y="24" text-anchor="middle" class="cls-header-text">Car</text>
    <line x1="0" y1="36" x2="195" y2="36" stroke="#1D4ED8" stroke-width="1"/>
    <text x="12" y="60" class="code-member">~ Car(regNo, pass)</text>
    <text x="12" y="82" class="code-member">~ ratePerHour(): double</text>
  </g>

  <!-- Sub-subclass: ElectricCar -->
  <g transform="translate(40, 375)" filter="url(#soft-shadow)">
    <rect width="240" height="120" rx="8" fill="#FFFFFF" stroke="#4338CA" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 232,0 A 8,8 0 0,1 240,8 L 240,36 L 0,36 Z" fill="#4338CA"/>
    <text x="120" y="24" text-anchor="middle" class="cls-header-text">ElectricCar</text>
    <line x1="0" y1="36" x2="240" y2="36" stroke="#4338CA" stroke-width="1"/>
    <text x="12" y="60" class="code-member">~ ElectricCar(reg, pass)</text>
    <text x="12" y="82" class="code-member">+ chargingFee(units): dbl</text>
    <text x="12" y="100" class="code-comment">/* units * 12.0 */</text>
  </g>

  <!-- Class: ParkingTicket -->
  <g transform="translate(680, 95)" filter="url(#soft-shadow)">
    <rect width="240" height="185" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 232,0 A 8,8 0 0,1 240,8 L 240,40 L 0,40 Z" fill="#475569"/>
    <text x="120" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;allocation state&gt;&gt;</text>
    <text x="120" y="34" text-anchor="middle" class="cls-header-text">ParkingTicket</text>
    <line x1="0" y1="40" x2="240" y2="40" stroke="#475569" stroke-width="1"/>
    <!-- Attributes -->
    <text x="14" y="64" class="code-member">~ vehicle: Vehicle</text>
    <text x="14" y="84" class="code-member">~ start: int</text>
    <text x="14" y="104" class="code-member">~ floor: int, slot: int</text>
    <line x1="0" y1="116" x2="240" y2="116" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="14" y="140" class="code-member">~ ParkingTicket(v, start,</text>
    <text x="26" y="158" class="code-member">  floor, slot)</text>
  </g>

  <!-- Class: ParkingFacility -->
  <g transform="translate(760, 340)" filter="url(#soft-shadow)">
    <rect width="260" height="220" rx="8" fill="#FFFFFF" stroke="#1E3A8A" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 252,0 A 8,8 0 0,1 260,8 L 260,40 L 0,40 Z" fill="#1E3A8A"/>
    <text x="130" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;system aggregate&gt;&gt;</text>
    <text x="130" y="34" text-anchor="middle" class="cls-header-text">ParkingFacility</text>
    <line x1="0" y1="40" x2="260" y2="40" stroke="#1E3A8A" stroke-width="1"/>
    <!-- Attributes -->
    <text x="12" y="64" class="code-member">~ occupied: boolean[][]</text>
    <text x="12" y="84" class="code-member">~ tickets: List&lt;Ticket&gt;</text>
    <text x="12" y="104" class="code-member">~ revenue: double</text>
    <line x1="0" y1="116" x2="260" y2="116" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="12" y="138" class="code-member">~ ParkingFacility(f, s)</text>
    <text x="12" y="160" class="code-member">~ checkIn(v, time): Ticket</text>
    <text x="12" y="182" class="code-member">~ checkOut(t, time,</text>
    <text x="24" y="200" class="code-member">  units): double</text>
  </g>

  <!-- Connectors (Zero Crossing) -->
  <!-- TwoWheeler to Vehicle -->
  <path d="M 417,375 L 417,330" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- Car to Vehicle -->
  <path d="M 637,375 L 637,350 L 530,350 L 530,330" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- ElectricCar to Car -->
  <path d="M 280,410 L 540,410" fill="none" stroke="#1E3A8A" stroke-width="2" marker-end="url(#generalization)"/>

  <!-- ElectricCar to Chargeable -->
  <path d="M 160,375 L 160,205" fill="none" stroke="#4338CA" stroke-width="2" stroke-dasharray="6,4" marker-end="url(#realization)"/>

  <!-- ParkingTicket to Vehicle -->
  <path d="M 680,180 L 630,180" fill="none" stroke="#1E293B" stroke-width="1.8" marker-end="url(#assoc-arrow)"/>
  <text x="635" y="172" class="mult-label">1</text>
  <text x="668" y="172" class="mult-label">1</text>

  <!-- ParkingFacility to ParkingTicket -->
  <path d="M 830,340 L 830,280" fill="none" stroke="#1E3A8A" stroke-width="2" marker-start="url(#aggregation)"/>
  <text x="838" y="325" class="mult-label">1</text>
  <text x="838" y="295" class="mult-label">*</text>

  <!-- Benchmark Panel -->
  <g transform="translate(40, 520)" filter="url(#soft-shadow)">
    <rect width="695" height="175" rx="8" fill="#F8FAFC" stroke="#CBD5E1" stroke-width="1.5"/>
    <rect width="6" height="175" rx="3" fill="#2563EB"/>
    
    <text x="20" y="26" font-family="'Segoe UI', sans-serif" font-size="12.5" font-weight="bold" fill="#1E3A8A">TASK 11 VERIFIED REVENUE BENCHMARK (Exact Match: Rs 230.0)</text>
    <line x1="20" y1="34" x2="680" y2="34" stroke="#E2E8F0" stroke-width="1.2"/>
    
    <text x="20" y="56" class="card-body">&#x2022; Car (TN02B2, 120 min @ Rs 30/hr): 2 hrs * 30 = Rs 60.0</text>
    <text x="20" y="76" class="card-body">&#x2022; Bike (TN01A1, 90 min @ Rs 10/hr): ceil(90/60) = 2 hrs * 10 = Rs 20.0</text>
    <text x="20" y="96" class="card-body">&#x2022; EV (TN03C3, 60 min park + 10 kWh charge): 1 hr * 30 (Rs 30) + 10 * 12 (Rs 120) = Rs 150.0</text>
    <text x="20" y="116" class="card-body">&#x2022; Monthly Pass Car (TN04D4, 60 min): monthlyPass=true &#x2192; Rs 0.0 waived; Zero-Min: Rs 0.0</text>
    <text x="20" y="142" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#15803D">&#x2714; Total System Revenue Collected: Rs 60.0 + Rs 20.0 + Rs 150.0 + Rs 0.0 + Rs 0.0 = Rs 230.0</text>
  </g>
</svg>"""
    save_figma_diagram(svg, "task11_core_system")

# ==============================================================================
# 4. DIAGRAM D: Task 11 - Vehicle Check-In Sequence Diagram
# ==============================================================================
def build_diagram_d():
    svg = f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 940 640" width="940" height="640">
{SVG_COMMON_DEFS}
  <!-- Canvas Background -->
  <rect width="100%" height="100%" fill="#F8FAFC" rx="14"/>
  <rect x="1" y="1" width="938" height="638" fill="none" stroke="#E2E8F0" stroke-width="1.5" rx="13"/>

  <!-- Canvas Header -->
  <text x="35" y="42" class="canvas-title">Task 11: Vehicle Check-In Sequence Diagram</text>
  <text x="35" y="64" class="canvas-subtitle">Formal UML 2.5 Dynamic Interaction Trace | Slot Allocation, Duplicate Check &amp; Ticket Creation</text>
  <line x1="35" y1="76" x2="905" y2="76" stroke="#E2E8F0" stroke-width="1.5"/>

  <!-- Lifeline 1: Driver / MainApp -->
  <g transform="translate(130, 95)">
    <rect x="-70" y="0" width="140" height="38" rx="6" fill="#1E293B" stroke="#0F172A" stroke-width="1.5" filter="url(#soft-shadow)"/>
    <text x="0" y="24" text-anchor="middle" class="seq-head">:Driver / MainApp</text>
    <line x1="0" y1="38" x2="0" y2="520" stroke="#94A3B8" stroke-width="1.5" stroke-dasharray="5,4"/>
    <rect x="-7" y="60" width="14" height="430" fill="#E2E8F0" stroke="#64748B" stroke-width="1.2"/>
  </g>

  <!-- Lifeline 2: ParkingFacility -->
  <g transform="translate(470, 95)">
    <rect x="-85" y="0" width="170" height="38" rx="6" fill="#1E3A8A" stroke="#1E40AF" stroke-width="1.5" filter="url(#soft-shadow)"/>
    <text x="0" y="24" text-anchor="middle" class="seq-head">facility:ParkingFacility</text>
    <line x1="0" y1="38" x2="0" y2="520" stroke="#94A3B8" stroke-width="1.5" stroke-dasharray="5,4"/>
    <rect x="-7" y="60" width="14" height="410" fill="#DBEAFE" stroke="#2563EB" stroke-width="1.2"/>
  </g>

  <!-- Lifeline 3: ParkingTicket -->
  <g transform="translate(790, 95)">
    <rect x="-70" y="0" width="140" height="38" rx="6" fill="#475569" stroke="#334155" stroke-width="1.5" filter="url(#soft-shadow)"/>
    <text x="0" y="24" text-anchor="middle" class="seq-head">t:ParkingTicket</text>
    <line x1="0" y1="38" x2="0" y2="520" stroke="#94A3B8" stroke-width="1.5" stroke-dasharray="5,4"/>
    <rect x="-7" y="335" width="14" height="70" fill="#F1F5F9" stroke="#64748B" stroke-width="1.2"/>
  </g>

  <!-- Step 1: checkIn(v, time) -->
  <g transform="translate(137, 165)">
    <line x1="0" y1="0" x2="326" y2="0" stroke="#0F172A" stroke-width="1.8" marker-end="url(#seq-call)"/>
    <text x="155" y="-10" text-anchor="middle" class="seq-call-text">1: checkIn(v: Vehicle, time: int)</text>
  </g>

  <!-- Step 2: Loop check duplicates -->
  <g transform="translate(477, 195)">
    <path d="M 0,0 L 35,0 L 35,24 L 0,24" fill="none" stroke="#0F172A" stroke-width="1.5" marker-end="url(#seq-call)"/>
    <text x="45" y="16" class="seq-call-text">2: for (t : tickets) check equals(v)</text>
  </g>

  <!-- Guard 1: Duplicate Found -->
  <g transform="translate(200, 240)">
    <rect width="540" height="32" rx="4" fill="#FEF2F2" stroke="#FCA5A5" stroke-width="1" stroke-dasharray="4,3"/>
    <text x="12" y="20" font-family="'Segoe UI', sans-serif" font-size="11" font-weight="bold" fill="#991B1B">[opt: Duplicate Found] return null (Reject double booking)</text>
  </g>

  <!-- Step 3: Scan 2D Matrix -->
  <g transform="translate(477, 285)">
    <path d="M 0,0 L 35,0 L 35,24 L 0,24" fill="none" stroke="#0F172A" stroke-width="1.5" marker-end="url(#seq-call)"/>
    <text x="45" y="16" class="seq-call-text">3: scan occupied[f][s] == false</text>
  </g>

  <!-- Guard 2: Full Facility -->
  <g transform="translate(200, 325)">
    <rect width="540" height="32" rx="4" fill="#FEF2F2" stroke="#FCA5A5" stroke-width="1" stroke-dasharray="4,3"/>
    <text x="12" y="20" font-family="'Segoe UI', sans-serif" font-size="11" font-weight="bold" fill="#991B1B">[opt: No Free Slot] return null (Facility full)</text>
  </g>

  <!-- Step 4: Mark Occupied -->
  <g transform="translate(477, 370)">
    <path d="M 0,0 L 35,0 L 35,20 L 0,20" fill="none" stroke="#0F172A" stroke-width="1.5" marker-end="url(#seq-call)"/>
    <text x="45" y="15" class="seq-call-text">4: occupied[f][s] = true</text>
  </g>

  <!-- Step 5: Create Ticket -->
  <g transform="translate(477, 430)">
    <line x1="0" y1="0" x2="306" y2="0" stroke="#0F172A" stroke-width="1.8" marker-end="url(#seq-call)"/>
    <text x="150" y="-10" text-anchor="middle" class="seq-call-text">5: &lt;&lt;create&gt;&gt; new ParkingTicket(v, time, f, s)</text>
  </g>

  <!-- Step 6: Return Ticket Instance -->
  <g transform="translate(477, 465)">
    <line x1="306" y1="0" x2="0" y2="0" stroke="#2563EB" stroke-width="1.8" stroke-dasharray="5,4" marker-end="url(#seq-ret)"/>
    <text x="150" y="-10" text-anchor="middle" class="seq-ret-text">6: ticket instance</text>
  </g>

  <!-- Step 7: Add to Collection -->
  <g transform="translate(477, 490)">
    <path d="M 0,0 L 35,0 L 35,20 L 0,20" fill="none" stroke="#0F172A" stroke-width="1.5" marker-end="url(#seq-call)"/>
    <text x="45" y="15" class="seq-call-text">7: tickets.add(t)</text>
  </g>

  <!-- Step 8: Return Ticket to Driver -->
  <g transform="translate(137, 535)">
    <line x1="326" y1="0" x2="0" y2="0" stroke="#2563EB" stroke-width="2" stroke-dasharray="5,4" marker-end="url(#seq-ret)"/>
    <text x="160" y="-10" text-anchor="middle" class="seq-ret-text">8: return t (allocation success)</text>
  </g>

  <!-- Footer Verification Note -->
  <g transform="translate(130, 565)">
    <rect width="730" height="60" rx="6" fill="#F0FDF4" stroke="#86EFAC" stroke-width="1.2"/>
    <rect width="5" height="60" rx="2" fill="#10B981"/>
    <text x="16" y="20" font-family="'Segoe UI', sans-serif" font-size="11.5" font-weight="bold" fill="#065F46">DYNAMIC BEHAVIOR &amp; ERROR PATH VERIFICATION (Task 11)</text>
    <text x="16" y="38" font-family="'Segoe UI', sans-serif" font-size="11" fill="#1E293B">&#x2022; Duplicate Prevention: Re-checkIn with same plate aborts at step 2 returning null.</text>
    <text x="16" y="52" font-family="'Segoe UI', sans-serif" font-size="11" fill="#1E293B">&#x2022; Capacity Invariant: When 2D matrix is saturated, check-in returns null without ticket creation.</text>
  </g>
</svg>"""
    save_figma_diagram(svg, "sequence_checkin")

# ==============================================================================
# 5. DIAGRAM E: Task 12 - Adapted Architecture (Strategy Pattern & Passes)
# ==============================================================================
def build_diagram_e():
    svg = f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1080 720" width="1080" height="720">
{SVG_COMMON_DEFS}
  <!-- Canvas Background -->
  <rect width="100%" height="100%" fill="#F8FAFC" rx="14"/>
  <rect x="1" y="1" width="1078" height="718" fill="none" stroke="#E2E8F0" stroke-width="1.5" rx="13"/>

  <!-- Canvas Header -->
  <text x="35" y="42" class="canvas-title">Task 12: Day-4 Adapted Architecture (Strategy Pattern &amp; Pass Subsystem)</text>
  <text x="35" y="64" class="canvas-subtitle">Decoupled Dynamic Billing Policies, 5-Floor Non-Uniform Layout &amp; Open-Closed Principle Defense</text>
  <line x1="35" y1="76" x2="1045" y2="76" stroke="#E2E8F0" stroke-width="1.5"/>

  <!-- ================= STRATEGY HIERARCHY ================= -->
  <!-- Interface: PricingStrategy -->
  <g transform="translate(380, 95)" filter="url(#soft-shadow)">
    <rect width="300" height="115" rx="8" fill="#FFFFFF" stroke="#4338CA" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 292,0 A 8,8 0 0,1 300,8 L 300,40 L 0,40 Z" fill="#4338CA"/>
    <text x="150" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;interface&gt;&gt;</text>
    <text x="150" y="34" text-anchor="middle" class="cls-header-text">PricingStrategy</text>
    <line x1="0" y1="40" x2="300" y2="40" stroke="#4338CA" stroke-width="1"/>
    <text x="12" y="66" class="code-member">+ calculateFee(entry, exit): double</text>
    <text x="12" y="86" class="code-member">+ calculateFee(hours: int): double</text>
    <text x="12" y="104" class="code-comment">/* dynamic time-of-day strategy contract */</text>
  </g>

  <!-- Concrete Strategy 1: HourlyPricing -->
  <g transform="translate(280, 275)" filter="url(#soft-shadow)">
    <rect width="215" height="120" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 207,0 A 8,8 0 0,1 215,8 L 215,36 L 0,36 Z" fill="#1E3A8A"/>
    <text x="107" y="24" text-anchor="middle" class="cls-header-text">HourlyPricing</text>
    <line x1="0" y1="36" x2="215" y2="36" stroke="#1E3A8A" stroke-width="1"/>
    <!-- Attributes -->
    <text x="10" y="58" class="code-member">- ratePerHour: double</text>
    <line x1="0" y1="68" x2="215" y2="68" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="10" y="90" class="code-member">+ HourlyPricing(rate)</text>
    <text x="10" y="110" class="code-member">+ calculateFee(entry, exit)</text>
  </g>

  <!-- Concrete Strategy 2: FlatPricing -->
  <g transform="translate(520, 275)" filter="url(#soft-shadow)">
    <rect width="215" height="120" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 207,0 A 8,8 0 0,1 215,8 L 215,36 L 0,36 Z" fill="#0F766E"/>
    <text x="107" y="24" text-anchor="middle" class="cls-header-text">FlatPricing</text>
    <line x1="0" y1="36" x2="215" y2="36" stroke="#0F766E" stroke-width="1"/>
    <!-- Attributes -->
    <text x="10" y="58" class="code-member">- flatFee: double = 15.0</text>
    <line x1="0" y1="68" x2="215" y2="68" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="10" y="90" class="code-member">+ FlatPricing(fee)</text>
    <text x="10" y="110" class="code-member">+ calculateFee(entry, exit)</text>
  </g>

  <!-- Concrete Strategy 3: DeliveryPricing -->
  <g transform="translate(760, 275)" filter="url(#soft-shadow)">
    <rect width="225" height="135" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 217,0 A 8,8 0 0,1 225,8 L 225,36 L 0,36 Z" fill="#D97706"/>
    <text x="112" y="24" text-anchor="middle" class="cls-header-text">DeliveryPricing</text>
    <line x1="0" y1="36" x2="225" y2="36" stroke="#D97706" stroke-width="1"/>
    <!-- Methods -->
    <text x="10" y="58" class="code-member">+ calculateFee(entry, exit)</text>
    <text x="16" y="74" class="code-comment">/* 9 AM-5 PM: Rs 60.0/hr */</text>
    <text x="16" y="90" class="code-comment">/* Off-Peak: Rs 30.0/hr */</text>
    <text x="16" y="106" class="code-comment">/* Boundary split &amp; ceiling */</text>
    <text x="16" y="122" class="code-comment">/* (pk_m+59)/60 + (off_m+59)/60 */</text>
  </g>

  <!-- ================= DOMAIN ENTITY: ParkingVehicle ================= -->
  <g transform="translate(35, 95)" filter="url(#soft-shadow)">
    <rect width="280" height="155" rx="8" fill="#FFFFFF" stroke="#047857" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 272,0 A 8,8 0 0,1 280,8 L 280,40 L 0,40 Z" fill="#047857"/>
    <text x="140" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;context entity&gt;&gt;</text>
    <text x="140" y="34" text-anchor="middle" class="cls-header-text">ParkingVehicle</text>
    <line x1="0" y1="40" x2="280" y2="40" stroke="#047857" stroke-width="1"/>
    <!-- Attributes -->
    <text x="12" y="62" class="code-member">~ regNo: String</text>
    <text x="12" y="82" class="code-member">~ pricing: PricingStrategy</text>
    <line x1="0" y1="92" x2="280" y2="92" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="12" y="114" class="code-member">~ ParkingVehicle(reg, pricing)</text>
    <text x="12" y="136" class="code-member">+ fee(entry, exit): double</text>
  </g>

  <!-- ================= PASS SUBSYSTEM ================= -->
  <!-- Interface: Pass -->
  <g transform="translate(770, 95)" filter="url(#soft-shadow)">
    <rect width="220" height="95" rx="8" fill="#FFFFFF" stroke="#6D28D9" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 212,0 A 8,8 0 0,1 220,8 L 220,36 L 0,36 Z" fill="#6D28D9"/>
    <text x="110" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;interface&gt;&gt;</text>
    <text x="110" y="32" text-anchor="middle" class="cls-header-text">Pass</text>
    <line x1="0" y1="36" x2="220" y2="36" stroke="#6D28D9" stroke-width="1"/>
    <text x="12" y="60" class="code-member">+ isValidAt(time): boolean</text>
    <text x="12" y="80" class="code-member">+ getPrice(): double</text>
  </g>

  <!-- Class: OneDayPass -->
  <g transform="translate(770, 210)" filter="url(#soft-shadow)">
    <rect width="220" height="105" rx="8" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 212,0 A 8,8 0 0,1 220,8 L 220,32 L 0,32 Z" fill="#7C3AED"/>
    <text x="110" y="21" text-anchor="middle" class="cls-header-text">OneDayPass</text>
    <text x="10" y="50" class="code-member">- price: double = 100.0</text>
    <text x="10" y="68" class="code-member">- issueTime, expiryTime</text>
    <text x="10" y="86" class="code-member">+ isValidAt(time): boolean</text>
    <text x="10" y="99" class="code-comment">/* issueTime + 24 hrs window */</text>
  </g>

  <!-- ================= ADAPTED FACILITY ================= -->
  <!-- Class: AdaptedParkingFacility -->
  <g transform="translate(35, 275)" filter="url(#soft-shadow)">
    <rect width="220" height="185" rx="8" fill="#FFFFFF" stroke="#1E3A8A" stroke-width="1.5"/>
    <path d="M 0,8 A 8,8 0 0,1 8,0 L 212,0 A 8,8 0 0,1 220,8 L 220,40 L 0,40 Z" fill="#1E3A8A"/>
    <text x="110" y="18" text-anchor="middle" class="cls-meta-text">&lt;&lt;5-floor facility&gt;&gt;</text>
    <text x="110" y="34" text-anchor="middle" class="cls-header-text">AdaptedFacility</text>
    <line x1="0" y1="40" x2="220" y2="40" stroke="#1E3A8A" stroke-width="1"/>
    <!-- Attributes -->
    <text x="10" y="62" class="code-member">~ floors: String[5][]</text>
    <text x="18" y="78" font-family="'Segoe UI', sans-serif" font-size="10.5" fill="#475569">&#x2022; {{30, 25, 20, 15, 10}}</text>
    <line x1="0" y1="88" x2="220" y2="88" stroke="#F1F5F9" stroke-width="1.2"/>
    <!-- Methods -->
    <text x="10" y="110" class="code-member">+ park(regNo): bool</text>
    <text x="10" y="130" class="code-member">+ remove(regNo): bool</text>
    <text x="10" y="150" class="code-member">+ totalSlots(): int</text>
    <text x="18" y="168" class="code-comment">/* 100 slots total */</text>
  </g>

  <!-- Connectors (Clean Orthogonal, Zero Overlap) -->
  <!-- Strategy Implementations to PricingStrategy -->
  <path d="M 387,275 L 387,240 L 490,240 L 490,210" fill="none" stroke="#4338CA" stroke-width="2" stroke-dasharray="6,4" marker-end="url(#realization)"/>
  <path d="M 627,275 L 627,240 L 530,240 L 530,210" fill="none" stroke="#4338CA" stroke-width="2" stroke-dasharray="6,4" marker-end="url(#realization)"/>
  <path d="M 870,275 L 870,240 L 570,240 L 570,210" fill="none" stroke="#4338CA" stroke-width="2" stroke-dasharray="6,4" marker-end="url(#realization)"/>

  <!-- OneDayPass to Pass -->
  <path d="M 880,210 L 880,190" fill="none" stroke="#6D28D9" stroke-width="2" stroke-dasharray="6,4" marker-end="url(#realization)"/>

  <!-- ParkingVehicle to PricingStrategy -->
  <path d="M 315,150 L 380,150" fill="none" stroke="#1E3A8A" stroke-width="2" marker-start="url(#aggregation)" marker-end="url(#assoc-arrow)"/>
  <text x="325" y="142" class="mult-label">1</text>
  <text x="365" y="142" class="mult-label">1</text>

  <!-- Architectural Defense & OCP Panel -->
  <g transform="translate(35, 485)" filter="url(#soft-shadow)">
    <rect width="1010" height="215" rx="8" fill="#FFFDF5" stroke="#FDE68A" stroke-width="1.5"/>
    <rect width="6" height="215" rx="3" fill="#D97706"/>
    
    <text x="20" y="28" class="card-title">TASK 12 ARCHITECTURAL DEFENSE &amp; VERIFIED POLICIES</text>
    <line x1="20" y1="36" x2="990" y2="36" stroke="#FEF3C7" stroke-width="1.2"/>

    <text x="20" y="60" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#78350F">1. Open-Closed Principle (OCP) Invariant:</text>
    <text x="28" y="80" class="card-body">&#x2022; Subsystem is open for extension but closed for modification. Introducing new vehicle categories (e.g., Autonomous Shuttles, Drones) or pricing schemes</text>
    <text x="28" y="98" class="card-body">  requires creating a new PricingStrategy class without altering a single line of ParkingVehicle or AdaptedParkingFacility code.</text>

    <text x="20" y="124" font-family="'Segoe UI', sans-serif" font-size="12" font-weight="bold" fill="#78350F">2. Verified Business Rules &amp; Dynamic Pricing Policies:</text>
    <text x="28" y="144" class="card-body">&#x2022; E-Bike Flat Fee: FlatPricing(15.0) guarantees fixed Rs 15.0 regardless of whether parked 30 minutes or 10 hours.</text>
    <text x="28" y="162" class="card-body">&#x2022; Delivery Van Time-of-Day Window: 10:00&#x2013;12:00 peak (Rs 60/hr) &#x2192; Rs 120.0; 06:00&#x2013;08:00 off-peak (Rs 30/hr) &#x2192; Rs 60.0; 08:00&#x2013;10:00 boundary (1h off-peak + 1h peak) &#x2192; Rs 90.0.</text>
    <text x="28" y="180" class="card-body">&#x2022; Visitor Pass: OneDayPass costs Rs 100.0 (24h window); valid exit at 5h waives fee (Rs 0.0); expired exit at 26h charges standard stay (Rs 60.0).</text>
    <text x="28" y="198" class="card-body">&#x2022; Non-Uniform Structural Capacity: Floor 0 (30) + Floor 1 (25) + Floor 2 (20) + Floor 3 (15) + Floor 4 (10) = 100 Total Slots exactly.</text>
  </g>
</svg>"""
    save_figma_diagram(svg, "task12_adapted_system")

def save_figma_diagram(svg_content, name):
    svg_path = os.path.join(OUTPUT_DIR, f"{name}.svg")
    png_path = os.path.join(OUTPUT_DIR, f"{name}.png")

    with open(svg_path, "w", encoding="utf-8") as f:
        f.write(svg_content.strip())

    # High-resolution rasterization at 300 DPI via PyMuPDF
    doc = pymupdf.open(stream=svg_content.encode("utf-8"), filetype="svg")
    page = doc[0]
    pix = page.get_pixmap(dpi=300)
    pix.save(png_path)
    print(f"Generated: {name}")
    print(f"  - SVG: {svg_path}")
    print(f"  - PNG: {png_path} ({pix.width}x{pix.height}, {os.path.getsize(png_path)} bytes)")

if __name__ == "__main__":
    print("Generating Publication-Quality Figma-Grade UML Diagrams (A4 Optimized)...")
    build_diagram_a()
    build_diagram_b()
    build_diagram_c()
    build_diagram_d()
    build_diagram_e()
    print("All 5 diagrams successfully generated!")
