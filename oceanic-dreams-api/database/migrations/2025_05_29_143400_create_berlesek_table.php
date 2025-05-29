<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('berlesek', function (Blueprint $table) {
        $table->id();
        $table->unsignedBigInteger('uid');
        $table->unsignedBigInteger('yachtId');
        $table->dateTime('startDate');
        $table->dateTime('endDate');
        $table->integer('dailyPrice');
        $table->integer('deposit');
        $table->timestamps();
    });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('berlesek');
    }
};
